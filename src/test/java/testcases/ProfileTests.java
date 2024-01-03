package testcases;

import base.BaseTest;
import db.KoelDbTests;
import org.mariadb.jdbc.Connection;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import db.*;
import util.extentReports.ExtentManager;

public class ProfileTests extends BaseTest {
    ResultSet rs;
    ProfilePage profilePage;
    LoginPage loginPage;
    HomePage homePage;
    KoelDbActions dbActions;

    public ProfileTests() {
        super();
    }
    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
    @BeforeClass
    public void setEnv() {
        loadEnv();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException, SQLException, ClassNotFoundException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        profilePage = loginPage.loginValidCredentials().clickAvatar();
        KoelDb.initializeDb();
    }
    @AfterMethod
    public void close() throws SQLException, ClassNotFoundException{
        closeBrowser();
        KoelDb.closeDatabaseConnection();
    }

    @Test(description = "Update profile name")
    public void changeProfileName()  throws InterruptedException {
        String randomNm = generateRandomName();
        String profileName = profilePage.getProfileName();
        try {
         profilePage
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .provideRandomProfileName(randomNm)
                    .clickSave();
            Assert.assertEquals(profileName, randomNm);
            Reporter.log("Updated user name to" + randomNm, true);
        }catch (Exception e) {
            Reporter.log("Failed to update user name" + e, true);
        }
    }
    private String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Test(description = "Update theme to In the Pines")
    public void choosePinesTheme() {
        try {
            profilePage
                    .clickTheme("pines");
            Assert.assertTrue(profilePage.verifyTheme("pines"));
            Reporter.log("Changed theme to pines", true);
        } catch(Exception e) {
            Reporter.log("Failed to change theme to pines theme" + e, true);
            Assert.assertFalse(false);
        }
    }
}
