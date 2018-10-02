package pages;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.FileUtils;

public class Driver
{
    private String tempDir;

    public Driver(String tempDir) {
        this.tempDir = tempDir;
    }

    public WebDriver getFFdriver()
    {
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(createProfile());

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        return driver;
    }

    private FirefoxProfile createProfile()
    {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/pdf,text/csv,application/scv,application/msexcel,application/zip,"
                        + "application/x-zip,application/x-zip-compressed,application/download,"
                        + "application/octet-stream");

        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", tempDir);
        profile.setPreference("webdriver_firefox_port", 4444);
        profile.setPreference("app.update.enabled", false);

        return profile;
    }
}
