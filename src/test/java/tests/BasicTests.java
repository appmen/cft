package tests;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import structure.Dashboard;
import structure.Driver;
import structure.Login;


public class BasicTests {

    private WebDriver driver = new Driver().getFFdriver();
    private static final String userName = "Test";
    private Login login;
    private Dashboard dashboard;
    private Assertion asserts = new Assertion();


    @BeforeClass
    public void setUp(){
        login = new Login(driver);
        dashboard = new Dashboard(driver);
        login.login();
    }

    @BeforeTest
    public void openHomepage(){
        driver.get(System.getProperty("url"));
    }

    /*@Test(priority = 1)
    public void verifyEntityExist(){
        dashboard.searchFor(userName);
        asserts.assertTrue (dashboard.getSearchResult(0)[0].contains(userName),
                "Verify that user name has updated version");
    }

    @Test(priority = 2)
    public void exportEntity(){
        dashboard.searchFor(userName);
        dashboard.makeAction(0, "Export");
        dashboard.exportEntity();
        asserts.assertEquals(dashboard.waitAndGetExportStatus(), "Completed",
        "Verify ability to export an entity");
    }

    @Test(priority = 3)
    public void downloadExportedEntity(){
        asserts.assertTrue(dashboard.getExportedEntity().contains("FN:"+userName),
                "Verify that exported file contains correct user name");
    }*/
    
    @Test(priority = 4)
    public void verifyExportDate() {
    	asserts.assertTrue(dashboard.isFirsRecordExportedToday(), 
    			"Verify that first record was exported today");
    }

    @AfterClass
    public void quit(){
        driver.quit();
    }

}
