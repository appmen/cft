package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Logout
{
    @FindBy(xpath = "//a[@ng-click='logout()']")
    private WebElement logoutButton;

    @FindBy (xpath = "//div[@class='col-xs-2 padding-top-xs text-right']/span/a[@class='btn btn-default btn-sm btn-notifications dropdown-toggle ng-binding']")
    private WebElement menu;
    
    public void logout() {
    	menu.click();
    	logoutButton.click();
    }

    public Logout(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

}
