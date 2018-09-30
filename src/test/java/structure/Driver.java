package structure;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Driver {
    WebDriver driver;


    public WebDriver getFFdriver(){
        //System.setProperty("webdriver.gecko.driver", System.getProperty("user.home")+"//Downloads//geckodriver.exe");
        /*DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser.download.folderList",2);
        cap.setCapability("browser.download.manager.showWhenStarting",false);
        cap.setCapability("browser.download.dir", System.getProperty("user.home")+"//Downloads");
        cap.setCapability("browser.helperApps.neverAsk.saveToDisk","application/zip, application/octet-stream, application/x-zip-compressed, multipart/x-zip");

        FirefoxProfile profile = new FirefoxProfile(new File(""));
        profile.setPreference("browser.download.folderList",2);
        profile.setPreference("browser.download.manager.showWhenStarting",false);
        profile.setPreference("browser.download.dir","c:\\mydownloads");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");

        cap.setCapability("browser.download.folderList",2);
        cap.setCapability("browser.download.manager.showWhenStarting",false);
        cap.setCapability("browser.download.dir", System.getProperty("user.home")+"//Downloads");
        cap.setCapability("browser.helperApps.neverAsk.saveToDisk","application/zip, application/octet-stream, application/x-zip-compressed, multipart/x-zip");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.setCapability("browser.download.folderList",2);
        options.setCapability("browser.download.manager.showWhenStarting",false);
        options.setCapability("browser.download.dir", System.getProperty("user.home")+"//Downloads");
        options.setCapability("browser.helperApps.neverAsk.saveToDisk","application/zip, application/octet-stream, application/x-zip-compressed, multipart/x-zip");
        */
        System.setProperty("webdriver.gecko.driver", System.getenv("USERPROFILE")+"\\Downloads\\GeckoDriver.exe");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf,text/csv,application/scv,application/msexcel,application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
        profile.setPreference("browser.download.folderList", "2");
        profile.setPreference("browser.download.dir", "d:\\");
        profile.setPreference("webdriver_firefox_port", 4444);
        profile.setPreference("app.update.enabled", false);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        driver = new FirefoxDriver(options);
        //driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        /*DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette",true);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        WebDriver driver= new FirefoxDriver(capabilities);*/

        return driver;
    }

    public WebDriver getChromeDriver(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")+"//Downloads//chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        return driver;
    }

}
