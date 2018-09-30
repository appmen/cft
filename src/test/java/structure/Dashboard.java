package structure;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.*;
import java.util.List;

public class Dashboard {
    private WebDriver driver;

    //Reports link at the left menu
    @FindBy (xpath="//a/span[contains(text(), 'Reports')]")
    private WebElement reportsLink;

    @FindBy (xpath="//input[@ng-model='searchQuery']")
    private WebElement searchField;

    @FindBy (xpath="//tr[@ng-repeat='result in searchResults']/tr[@class='ng-scope']")
    private List<WebElement> searchResult;

    @FindBy (xpath="//tr[@class='ng-scope']//td[@class='padding-mini']/input")
    private List<WebElement> searchResultcheckbox;

    @FindBy (xpath = "//button[contains(text(), 'Action')]")
    private WebElement actionDropdown;

    //Export button at export confirmation pop-up
    @FindBy (xpath = "//button[text()='Export']")
    private WebElement exportButton;

    @FindBy (xpath = "//button[text()='Close']")
    private WebElement closeButton;

    //Export tab at the Reports page
    @FindBy (xpath = "//li/a[text()='Export']")
    private WebElement exportTab;

    @FindBy (xpath = "//table[@class='table table-condensed']/tbody/tr[@class='ng-scope']/td[4]/p/span")
    private WebElement firstRecordStatus;

    @FindBy (xpath = "//table[@class='table table-condensed']/tbody/tr[@class='ng-scope']/td[5]/p/span/i")
    private WebElement downloadLink;

    @FindBy (xpath = "//i[@class='fa fa-download fa-pointer fa-hover-primary']")
    private WebElement downloadLinkAtDashboard;

    private void wait(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void searchFor(String criterion){
        Reporter.log("Search for " + criterion);
        searchField.clear();
        searchField.sendKeys(criterion);
        searchField.sendKeys(Keys.ENTER);
    }

    public String[] getSearchResult(int position){
        String[] result = new String[3];
        //get only title for now
        result[0] = searchResult.get(position).findElement(
                By.xpath("//td[@class='summary ng-binding']/strong")).getText();


        return result;
    }

    private void selectCheckbox(int position){
        Reporter.log("Click checkbox # " + position);
        searchResultcheckbox.get(position).click();
    }

    public void makeAction(int elementPosition, String actionName){
        selectCheckbox(elementPosition);

        Reporter.log("Click Actions drop-down");
        actionDropdown.click();

        Reporter.log("Select " + actionName);
        actionDropdown.findElement(By.xpath("//following-sibling::ul/li/a[text()='" + actionName + "']")).click();

    }

    public void exportEntity(){

        Reporter.log("Click 'Export' button");
        exportButton.click();

        Reporter.log("Click 'Close' at export confirmation pop-up");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(closeButton));
        closeButton.click();

    }

    public String waitAndGetExportStatus(){

        Reporter.log("Click 'Reports' link");
        wait(2);//wait until requests are finished
        reportsLink.click();

        Reporter.log("Click 'Export' tab");
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(exportTab));
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(exportTab));
        exportTab.click();

        Reporter.log("Wait for file to be exported:");
        for (int i=0; i<10; i++){
            if (firstRecordStatus.getText().equals("Completed")){
                break;
            }else{
                Reporter.log("Refresh page");
                driver.navigate().refresh();

                wait(2);
            }
        }

        return firstRecordStatus.getText();
    }

    public String getExportedEntity(){
    	//Get url for download folder
        String downloadFolder = System.getProperty("user.home")+"\\Downloads";
    	//Delete prev zip files
        String deleteZipFiles = String.format("cmd /C del /F /Q %s\\*.zip", downloadFolder);
        runCmdCommand(deleteZipFiles);
        
        Reporter.log("Click 'Export' tab");
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(exportTab));
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(exportTab));
        exportTab.click();

        Reporter.log("Click 'download' button");
        downloadLinkAtDashboard.click();
        
        //Get file name for downloaded file 
        String fileName = getfileName(downloadFolder);
        //Get full path to downloaded file
        String fullFilePath = downloadFolder + "\\" + fileName;
        //Extract zip archive
        String extractDownloadedFile = String.format("powershell.exe -command \"Expand-Archive -Path %s -DestinationPath %s\"",fullFilePath, downloadFolder);
        
        runCmdCommand(extractDownloadedFile);
        wait(3);
        
        String exportedFolder = fullFilePath.replace(".zip", "") + "\\vk@pxtx.onmicrosoft.com";
        
        String extractedFile = (new File(exportedFolder)).listFiles()[0].getName();
        
        String fileContent = getFileContent(exportedFolder + "\\" + extractedFile);
        
        //Delete extracted folder
        String deleteExtractedFolder = String.format("rmdir /C del /S /Q %s", exportedFolder);
        runCmdCommand(deleteExtractedFolder);
        
        return fileContent;
    }

    private String getfileName(String directory){
        File folder = new File(directory);
        File[] listOfFiles;

        for (int j = 0; j < 20; j++) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".zip") && listOfFiles[i].length() > 0) {

                    return listOfFiles[i].getName();
                }
            }
            wait(1);

        }
        return null;
    }

    private void runCmdCommand(String command){
        Runtime rt = Runtime.getRuntime();
        try {
            
            rt.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileContent(String extractedFile){
        BufferedReader br = null;
        String fileContent = "";
        try {
            br = new BufferedReader(new FileReader(extractedFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                line = br.readLine();
            }
            fileContent = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContent;
    }

    public Dashboard(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
