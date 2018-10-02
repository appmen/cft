package tests;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import pages.Dashboard;
import pages.Driver;
import pages.Login;
import pages.Logout;
import utils.FileUtils;

public class BasicTests
{
    private WebDriver driver;
    private static final String tempStorageDir = FileUtils.createTempDir("cft");
    private static final String userName = "Test";
    private Assertion asserts = new Assertion();

    @BeforeClass
    public void setup() {
    	driver = new Driver(tempStorageDir).getFFdriver();
    }
    
    @AfterClass
    public void quit(){
        driver.quit();
    }
    
    @BeforeMethod
    public void beforeTest() {
        new Login(driver).login();
        driver.get(System.getProperty("url"));
    }
    
    @AfterMethod
    public void afterTest() {
    	new Logout(driver).logout();
    }
    
    @Test(priority = 1)
    public void verifyEntityExist() {
    	Dashboard dashboard = new Dashboard(driver, tempStorageDir);
        dashboard.searchFor(userName);
        asserts.assertTrue (dashboard.getSearchResult(0)[0].contains(userName),
                "Verify that user name has updated version");
    }

    @Test(priority = 2)
    public void exportEntity() {
    	Dashboard dashboard = new Dashboard(driver, tempStorageDir);
        dashboard.searchFor(userName);
        dashboard.makeAction(0, "Export");
        dashboard.exportEntity();
        asserts.assertEquals(dashboard.waitAndGetExportStatus(), "Completed", "Verify ability to export an entity");
    }

    @Test(priority = 3)
    public void downloadExportedEntity() {
    	Dashboard dashboard = new Dashboard(driver, tempStorageDir);
        asserts.assertTrue(dashboard.getExportedEntity().contains("FN:"+userName),
                "Verify that exported file contains correct user name");
    }
    
    @Test(priority = 4)
    public void verifyExportDate() {
    	Dashboard dashboard = new Dashboard(driver, tempStorageDir);
    	asserts.assertTrue(dashboard.isFirsRecordExportedToday(), 
    			"Verify that first record was exported today");
    }
}
