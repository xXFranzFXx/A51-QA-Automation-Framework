package testcases;
import base.BaseTest;
import db.KoelDbBase;
import db.KoelDbActions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import util.TestUtil;
import util.listeners.TestListener;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @Test(description = "Verify error message when updating password less than 10 characters long", groups = {"Update password"})
    public void loginAndUpdatePwdIncorrectLength() {
        String newPassword = "fff";
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        try {
            homePage.clickAvatar();
            profilePage
                    .provideNewPassword(newPassword)
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .clickSaveButton();
            TestListener.logInfoDetails("New password length: " + newPassword.length());
            String errorMsgText = profilePage.getErrorNotificationText();
            TestListener.logRsDetails("Notification text: " + errorMsgText);
            TestListener.logAssertionDetails("Error message notification is displayed: " + profilePage.errorNotificationPopup());
            Assert.assertTrue(profilePage.errorNotificationPopup());
        } catch (Exception e) {
            TestListener.logExceptionDetails("Error " + e);
        }
    }
    @Test(description = "Verify error message for updating password with no number requirement", groups = {"Update password"})
    public void loginAndUpdatePwdNoNumbers() {
        String newPassword = "fffffffffff@";
        String expected = "The new password must contain at least one number.";
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        try {
            homePage.clickAvatar();
            profilePage
                    .provideNewPassword(newPassword)
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .clickSaveButton();
            TestListener.logInfoDetails("New password: " + newPassword);
            TestListener.logInfoDetails("New password length: " + newPassword.length());
            String errorMsgText = profilePage.getErrorNotificationText();
            TestListener.logRsDetails("Notification text: " + errorMsgText);
            TestListener.logAssertionDetails("Error message notification is displayed: " + profilePage.errorNotificationPopup());
            Assert.assertTrue(profilePage.errorNotificationPopup());
            Assert.assertEquals(expected.toLowerCase(), profilePage.getErrorNotificationText().toLowerCase());
        } catch (Exception e) {
            TestListener.logExceptionDetails("Error" + e);
        }
    }
    @Test(description = "Verify error message for updating password with no symbol requirement", groups = {"Update password"})
    public void loginAndUpdatePwdNoSymbols() {
        String newPassword = "fffffffffff1";
        String expected = "The new password must contain at least one symbol.";
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        try {
            homePage.clickAvatar();
            profilePage
                    .provideNewPassword(newPassword)
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .clickSaveButton();
            TestListener.logInfoDetails("New password: " + newPassword);
            TestListener.logInfoDetails("New password length: " + newPassword.length());
            String errorMsgText = profilePage.getErrorNotificationText();
            TestListener.logRsDetails("Notification text: " + errorMsgText);
            TestListener.logAssertionDetails("Error message notification is displayed: " + profilePage.errorNotificationPopup());
            Assert.assertTrue(profilePage.errorNotificationPopup());
            Assert.assertEquals(expected.toLowerCase(), profilePage.getErrorNotificationText().toLowerCase());
        } catch (Exception e) {
            TestListener.logExceptionDetails("Error " + e);
        }
    }
    @Test(description = "Verify error message for updating password that has appeared in data leak", groups = {"Update password"})
    public void loginAndUpdatePwdLeaked() {
        String newPassword = "newpassword!1";
        String expected = "The given new password has appeared in a data leak. Please choose a different new password.";
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        try {
            homePage.clickAvatar();
            profilePage
                    .provideNewPassword(newPassword)
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .clickSaveButton();
            TestListener.logInfoDetails("New password: " + newPassword);
            TestListener.logInfoDetails("New password length: " + newPassword.length());
            String errorMsgText = profilePage.getErrorNotificationText();
            TestListener.logRsDetails("Notification text: " + errorMsgText);
            TestListener.logAssertionDetails("Error message notification is displayed: " + profilePage.errorNotificationPopup());
            Assert.assertTrue(profilePage.errorNotificationPopup());
            Assert.assertEquals(expected.toLowerCase(), profilePage.getErrorNotificationText().toLowerCase());
        } catch (Exception e) {
            TestListener.logExceptionDetails("Error " + e);
        }
    }
    @Test(description = "Log in and update the password successfully", groups = {"Update password"})
    public void loginUpdatePwd() {
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
        profilePage = new ProfilePage(getDriver());
        try {
            homePage.clickAvatar();
            profilePage
                    .provideNewPassword(System.getProperty("updatedPassword"))
                    .provideCurrentPassword(System.getProperty("koelPassword"))
                    .clickSaveButton();
            TestListener.logInfoDetails("New Password: " + System.getProperty("updatedPassword"));
            TestListener.logInfoDetails("Old Password: " + System.getProperty("koelPassword"));
            TestListener.logAssertionDetails("User can login and update password: " + profilePage.notificationPopup());
            Assert.assertTrue(profilePage.notificationPopup());
        } catch (Exception e) {
            TestListener.logExceptionDetails("Failed to update password " + e);
        }
    }
    @Test(description = "Attempt to log in with old password", groups = {"Update password"})
    public void loginWithOldPwd() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        TestListener.logInfoDetails("Attempt logging in with password: " + System.getProperty("koelPassword"));
        TestListener.logAssertionDetails("User should not be able to log in with old password: " + loginPage.getRegistrationLink());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }
    @Test(description = "Log in successfully with updated password", groups = {"Update password"})
    public void loginWithUpdatedPwd() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.provideEmail(System.getProperty("koelUser"))
                .providePassword(System.getProperty("updatedPassword"))
                .clickSubmitBtn();
        TestListener.logInfoDetails("Updated password: " + System.getProperty("updatedPassword"));
        TestListener.logAssertionDetails("User should be able to log in with updated password: " + homePage.getUserAvatar());
        Assert.assertTrue(homePage.getUserAvatar());
    }
    @Test(description = "Execute SQL query to verify password is encrypted and has been updated in the Koel database", groups = {"Update password"})
    public void queryDbPwd() throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        TestListener.logInfoDetails("Db connection: " + KoelDbBase.getDbConnection().getMetaData().getURL());
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
        KoelDbBase.closeDatabaseConnection();
    }

    @Test(description = "Reset the Koel user profile", groups = {"Update password"})//(description = "resets user profile", dependsOnMethods = {"loginWithUpdatedPwd, queryDbPwd"})
    public void resetProfile() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        profilePage = new ProfilePage(getDriver());
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
            TestListener.logExceptionDetails("Failed to reset user profile " + e);
        }
    }
}
