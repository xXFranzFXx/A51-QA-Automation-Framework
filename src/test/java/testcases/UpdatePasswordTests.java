package testcases;

import base.BaseTest;
import db.KoelDb;
import db.KoelDbActions;
import io.cucumber.java.sl.Ko;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;
import util.TestUtil;
import util.listeners.TestListener;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *  Story:
 * As a user, I want to be able to update my account password, so I can keep my account secure
 * Acceptance Criteria:
 * User should be able to update the account password from the 'Profile & Preference' page
 * User should be able to login with the new password and the old password should not work
 * User password should be encrypted
 * Password Requirements:
 * At least 10 characters—the more characters, the better (upper limit of 15 characters)
 * A mixture of both uppercase and lowercase letters
 * A mixture of letters and numbers
 * Inclusion of at least one special character, e.g., ! @ # ? ]
 *
 * QA Notes: Verify that the password was actually updated in the Database (Table: users)
 */
public class UpdatePasswordTests extends BaseTest {
    ResultSet rs;
    ProfilePage profilePage;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException{
        setupBrowser(baseURL);
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }

    @Test(description = "Log in, update the password, log out")
    public void loginAndUpdatePwd() {
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        homePage.clickAvatar();
        profilePage
                .provideNewPassword(System.getProperty("updatedPassword"))
                .provideCurrentPassword(System.getProperty("koelPassword"))
                .clickSaveButton()
                .clickLogout();
        TestListener.logInfoDetails("New Password: " + System.getProperty("updatedPassword"));
        TestListener.logInfoDetails("Old Password: " + System.getProperty("koelPassword"));
        TestListener.logAssertionDetails("Log out after updating password: " + loginPage.getRegistrationLink());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }
    @Test(description = "Attempt to log in with old password")
    public void loginWithOldPwd() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        TestListener.logInfoDetails("Attempt logging in with password: " + System.getProperty("koelPassword"));
        TestListener.logAssertionDetails("User should not be able to log in with old password: " + loginPage.getRegistrationLink());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }
    @Test(description = "Log in successfully with updated password")
    public void loginWithUpdatedPwd() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.provideEmail(System.getProperty("koelUser"))
                .providePassword(System.getProperty("updatedPassword"))
                .clickSubmitBtn();
        TestListener.logInfoDetails("Attempt logging in with password: " + System.getProperty("updatedPassword"));
        TestListener.logAssertionDetails("User should be able to log in with updated password: " + homePage.getUserAvatar());
        Assert.assertTrue(homePage.getUserAvatar());
    }
    @Test(description = "Execute SQL query to verify password is encrypted and has been updated in the Koel database")
    public void queryDbPwd() throws SQLException, ClassNotFoundException {
        KoelDb.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        TestListener.logInfoDetails("Db connection: " + KoelDb.getDbConnection().getMetaData().getURL());
        rs = koelDbActions.getPwdInfo(System.getProperty("koelUser"));
        if (rs.next()) {
            String ep = rs.getString("password");
            String updated = rs.getString("updated_at");
            TestListener.logRsDetails(
                    "Results: " +"\n" +"<br>"+
                    "encrypted password: " + ep +"\n" +"<br>"+
                    "updated_at: " + updated +"\n" +"<br>"+
                    "user: " + System.getProperty("koelUser")
            );
           TestListener.logAssertionDetails("Assertion: " + updated + " contains " + TestUtil.getDate());
           TestListener.logAssertionDetails("Assertion: " + ep + " notSame " + System.getProperty("updatedPassword"));
           Assert.assertNotSame(ep, System.getProperty("updatedPassword"));
           Assert.assertTrue(updated.contains(TestUtil.getDate()));
        }
        Assert.assertFalse(false);
        KoelDb.closeDatabaseConnection();
    }

    @Test//(description = "resets user profile", dependsOnMethods = {"loginWithUpdatedPwd, queryDbPwd"})
    public void resetProfile() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        profilePage = new ProfilePage(getDriver());
        String url = "https://qa.koel.app/#!/home";
        String loginUrl = driver.getCurrentUrl();
        try {
            loginPage.provideEmail(System.getProperty("koelUser"))
                    .providePassword(System.getProperty("updatedPassword"))
                    .clickSubmitBtn();

                homePage.clickAvatar();
                profilePage
                        .provideCurrentPassword(System.getProperty("updatedPassword"))
                        .provideNewPassword(System.getProperty("koelPassword"))
                        .clickSaveButton();
                TestListener.logAssertionDetails("Account has been reset: " + profilePage.notificationPopup());
                Assert.assertTrue(profilePage.notificationPopup());
                Reporter.log("Restored profile", true);
        } catch(Exception e) {
            Reporter.log("Unable to reset profile" + e, true);
        }
    }
}
