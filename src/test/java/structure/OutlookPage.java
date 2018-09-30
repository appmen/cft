package structure;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.Set;

public class OutlookPage {
    private WebDriver driver;

    @FindBy (id = "i0116")
    private WebElement userField;

    @FindBy (id ="idSIButton9")
    private WebElement submitButton;

    @FindBy (id ="i0118")
    private WebElement passField;

    @FindBy(xpath = "//div[@title='Outlook']")
    private WebElement outlookLink;

    @FindBy(xpath = "//div[@class='__Microsoft_Owa_TriageShared_templates_cs_6 flex flexwrap']//div[3]/button")
    private WebElement contactsLink;

    @FindBy(id = "_ariaId_127")
    private WebElement myContactsLink;
    
    @FindBy(id = "_ariaId_75")
    private WebElement myContactsLink2;

    @FindBy(xpath = "//div[@class='_ph_i6 tableViewContainer scrollContainer']")
    private WebElement contactsTable;

    @FindBy(xpath = "//div[@class='_pf_f']/button[contains(@class, '_pf_d ms-font-xs ms-font-weight-semilight o365button')]")
    private WebElement dotsAtContactInfo;

    @FindBy (xpath = "//div[@class='_ph_t5']/div[3]/div/button")//[@class='_fce_i ms-fwt-r ms-fcl-np o365button']
    private WebElement editContactLink;

    @FindBy (xpath = "//div[@class='_fce_N _fce_x scrollContainer']/div/div/div[1]/button")
    private WebElement editContactLink2;

    @FindBy(xpath = "//div[@class='_cpc_j']//input[1]")
    private WebElement nameInput;

    @FindBy (xpath = "//div[@class='_cpc_9']//div[@class='_fce_e']/button[contains(@class, '_fce_i ms-fwt-r ms-fcl-np o365button')]")
    private WebElement saveContactButton;


    public void loginToOutlook(){
        driver.get("https://portal.office.com/");
        userField.sendKeys("vk@pxtx.onmicrosoft.com");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        passField.sendKeys("Qwerty123456");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();

        //(new WebDriverWait(driver, 10)).until(ExpectedConditions.attributeContains(submitButton, "value", "Yes"));
        submitButton.click();


    }

    public void editContact(String contactName){
        /*String parentWindow = driver.getWindowHandle();
        outlookLink.click();
        Set<String> allWindows = driver.getWindowHandles();
        allWindows.remove(parentWindow);
        driver.switchTo().window(allWindows.toArray(new String[0])[0]);
        driver.manage().window().fullscreen();
        contactsLink.click();

        
        try {
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(myContactsLink));
        }catch (TimeoutException e){
            //do nothing
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", myContactsLink);
        */
    	driver.get("https://outlook.office365.com/owa/?realm=pxtx.onmicrosoft.com&exsvurl=1&ll-cc=1058&modurl=0&path=/people");
    	System.out.println("#  "+ myContactsLink2.findElements(By.xpath(".")));
    	myContactsLink2.findElements(By.xpath(".")).get(0).click();
        System.out.println("#  "+editContactLink.findElements(By.xpath(".")).size());
        editContactLink.findElements(By.xpath(".")).get(1).click();
    	

    	contactsTable.findElement(By.xpath("//button/div[1]/span/span[@title='" + contactName + "']")).click();
        Actions act = new Actions(driver);
        /*act.moveToElement(dotsAtContactInfo).click(dotsAtContactInfo).build().perform();
        editContactLink2.click();*/
        nameInput.sendKeys("edited");

        System.out.println("#  "+driver.findElements(By.xpath("//div[@class='_cpc_9']//div[@class='_fce_e']/button[contains(@class, '_fce_i ms-fwt-r ms-fcl-np o365button')]")).size());
        act.moveToElement(saveContactButton).click(saveContactButton).build().perform();


    }

    public OutlookPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
