package structure;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.util.concurrent.TimeUnit;

public class Driver {
    WebDriver driver;


    public WebDriver getFFdriver(){
        
        System.setProperty("webdriver.gecko.driver", System.getenv("USERPROFILE")+"\\Downloads\\geckodriver.exe");
        
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf,text/csv,application/scv,application/msexcel,application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
        profile.setPreference("browser.download.folderList", "2");
        profile.setPreference("browser.download.dir", "d:\\");
        profile.setPreference("webdriver_firefox_port", 4444);
        profile.setPreference("app.update.enabled", false);
        
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        
        driver = new FirefoxDriver(options);
        
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        return driver;
    }

    public WebDriver getChromeDriver(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")+"//Downloads//chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        return driver;
    }

}
