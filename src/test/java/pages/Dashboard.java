package pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utils.FileUtils;
import utils.Unzipper;

public class Dashboard
{
    private WebDriver driver;
    private String exportDir;

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
    
    @FindBy(xpath = "(//table[@class='table table-striped table-condensed']/tbody/tr[1]/td[2])[2]")
    private WebElement endExportDate;

    public Dashboard(WebDriver driver, String exportDir) {
        this.driver = driver;
        this.exportDir = exportDir;
        PageFactory.initElements(driver, this);
    }

    public void searchFor(String criterion) {
        Reporter.log("Search for " + criterion);
        searchField.clear();
        searchField.sendKeys(criterion);
        searchField.sendKeys(Keys.ENTER);
    }

    public String[] getSearchResult(int position) {
        Reporter.log("Read search result, return title");
        String[] result = new String[3];
        
        //get only title for now
        result[0] = searchResult.get(position).findElement(
                By.xpath("//td[@class='summary ng-binding']/strong")).getText();

        return result;
    }

    private void selectCheckbox(int position) {
        Reporter.log("Click checkbox # " + position);
        searchResultcheckbox.get(position).click();
    }

    public void makeAction(int elementPosition, String actionName) {
        selectCheckbox(elementPosition);

        Reporter.log("Click Actions drop-down");
        actionDropdown.click();

        Reporter.log("Select " + actionName);
        actionDropdown.findElement(By.xpath("//following-sibling::ul/li/a[text()='" + actionName + "']")).click();
    }

    public void exportEntity() {
        Reporter.log("Click 'Export' button");
        exportButton.click();

        Reporter.log("Click 'Close' at export confirmation pop-up");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(closeButton));
        closeButton.click();
    }

    public String waitAndGetExportStatus()
    {
        Reporter.log("Click 'Reports' link");
        waitForAngularRequestsToFinish();
        reportsLink.click();

        Reporter.log("Click 'Export' tab");
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(exportTab));
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(exportTab));
        exportTab.click();

        Reporter.log("Wait for file to be exported:");
        for (int i = 0; i < 10; i++)
        {
            if (firstRecordStatus.getText().equals("Completed")) {
                break;
            }
            
            Reporter.log("Refresh page");
            driver.navigate().refresh();
            sleep(2);
        }

        return firstRecordStatus.getText();
    }

    public String getExportedEntity()
    {
        Reporter.log("Click 'Export' tab");
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(exportTab));
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(exportTab));
        exportTab.click();

        Reporter.log("Click 'download' button");
        downloadLinkAtDashboard.click();
        
        // get file name for downloaded file 
        String fileName = FileUtils.getfileName(exportDir);
        
        // get full path to downloaded file
        String fullFilePath = exportDir + File.separator + fileName;
        
        // extract zip archive
        try {
			Unzipper.unzip(Paths.get(fullFilePath), Paths.get(exportDir));
		} catch (IOException e) {
			System.err.println("Error while extracting " + fullFilePath + ". Error: " + e.getMessage());
		}
        
        String exportedFolder = fullFilePath.replace(".zip", "") + File.separator + "vk@pxtx.onmicrosoft.com";
        String extractedFile = (new File(exportedFolder)).listFiles()[0].getName();
        String fileContent = FileUtils.getFileContent(exportedFolder + File.separator + extractedFile);

        return fileContent;
    }

    public Boolean isFirsRecordExportedToday()
    {
    	Reporter.log("Click 'Export' tab");
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(exportTab));
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(exportTab));
        exportTab.click();
        
    	SimpleDateFormat format = new SimpleDateFormat("M/d/yy"); 
    	String today = format.format(new Date());
    	
    	return endExportDate.getText().trim().split(" ")[0].equals(today);
    }

    private void waitForAngularRequestsToFinish() {
        if ((boolean) ((JavascriptExecutor) driver).executeScript("return (typeof angular !== 'undefined')? true : false;")) {
            ((JavascriptExecutor) driver).executeAsyncScript("var callback = arguments[arguments.length - 1];" + "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
        }
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
