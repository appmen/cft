package structure;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class Login {
    private WebDriver driver;

    @FindBy (id = "username")
    private WebElement usernameField;

    @FindBy (id = "password")
    private WebElement passwordField;

    @FindBy (xpath = "//input[@value='Login']")
    private WebElement loginButton;

    public void login(){
        Reporter.log("Open URL");
        driver.get(System.getProperty("url"));

        Reporter.log("Type username");
        usernameField.sendKeys(System.getProperty("user"));

        Reporter.log("Type password");
        passwordField.sendKeys(System.getProperty("password"));

        Reporter.log("Click 'Login'");
        loginButton.click();
    }

    public Login(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
