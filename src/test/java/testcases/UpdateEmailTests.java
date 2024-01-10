package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.openqa.selenium.ElementClickInterceptedException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;
import util.TestDataHandler;
import util.TestUtil;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Story:
 * As a user, I want to update an account email.
 * Acceptance Criteria:
 * User should be able to update account email in app
 * Add validation to the email field: email must have @ symbol, dot and domain. Show error message if email is not valid
 * If the new email is already in the database, show the message "this user already exists"
 * User should be able to login into app with updated email
 * User should not be able to login into app with old email
 * The updated email should be correctly saved to the database
 *
 */

public class UpdateEmailTests extends BaseTest {
    LoginPage loginPage;
    ProfilePage profilePage;
    HomePage homePage;
    ResultSet rs;

    TestDataHandler testData = new TestDataHandler();
    Map<String, Object> dataMap = new HashMap<>();

    public Object getDataValue(String key) {
        Map<String, Object> testDataInMap = testData.getTestDataInMap();
        Object value = testDataInMap.get(key);
        System.out.println("value is: " + value);
        TestListener.logInfoDetails("Retrieved stored data: " + value);
        return value;
    }

    public void addDataFromTest(String key, Object value) {
        dataMap.put(key, value);
        testData.setTestDataInMap(dataMap);
        TestListener.logInfoDetails("Storing data: " + dataMap.toString());
    }

    public void clearDataFromTest() {
        dataMap.clear();
        testData.setTestDataInMap(dataMap);
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setUp(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }
    @Test(description = "Execute SQL query to find an existing user", priority=0)
    @Parameters({"koelExistingUser"})
    public void queryDbForExistingUser(String koelExistingUser) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.getUserInfo(koelExistingUser);
        if (rs.next()) {
            String email = rs.getString("email");
            TestListener.logRsDetails(
                    "Results: " + "\n" + "<br>" +
                            "user: " + email + "\n" + "<br>"
            );
            addDataFromTest("existingUser", email);   //store the account email to use for the next test
            TestListener.logRsDetails("Existing user: " + email);
            TestListener.logAssertionDetails("User already exists in database: " + email.equals(koelExistingUser));
            Assert.assertEquals(email, koelExistingUser);
        }
        KoelDbBase.closeDatabaseConnection();
    }
    @Test(description = "Verify user cannot update email address with one that already exists in the database", priority=1)
    @Parameters({"koelPassword"})
    public void updateEmailWithExisting(String koelPassword) {
        String existingUser = getDataValue("existingUser").toString();
        String expectedErrorMsg = "The email has already been taken.";
        TestListener.logInfoDetails("Expected error message: " + expectedErrorMsg);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
        profilePage.provideCurrentPassword(koelPassword)
                .provideEmail(existingUser)
                .clickSaveButton();
        String errorMsg = profilePage.getErrorNotificationText();
        TestListener.logRsDetails("Actual error message: " + errorMsg);
        TestListener.logAssertionDetails("Correct error message was displayed: " + expectedErrorMsg.equalsIgnoreCase(errorMsg));
        Assert.assertEquals(expectedErrorMsg.toLowerCase(), errorMsg.toLowerCase());
    }
    @Test(description =  "Verify form validation error message when updating email with no '@'.")
    @Parameters({"email", "password"})
    public void updateWithIncorrectEmailFmt(String email, String password){
        TestListener.logInfoDetails("String email: " + email);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
        profilePage.provideCurrentPassword(password)
                .provideEmail(email)
                .clickSaveButton();
        String expected = "Please include an '@' in the email address. '"+email+"' is missing an '@'.";
        String errorMsg = profilePage.getValidationMsg();
        TestListener.logInfoDetails("Expected String: " + expected);
        TestListener.logRsDetails("Actual String: " + errorMsg);
        TestListener.logAssertionDetails("Correct error message was displayed: " + expected.equalsIgnoreCase(errorMsg));
        Assert.assertEquals(expected.toLowerCase(), errorMsg.toLowerCase());
    }
    @Test(description =  "Verify form validation error message when updating email with incorrect format")
    @Parameters({"incompleteEmail", "password"})
    public void updateWithIncompleteEmail(String incompleteEmail, String password){
        TestListener.logInfoDetails("String email: " + incompleteEmail);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
        profilePage.provideCurrentPassword(password)
                .provideEmail(incompleteEmail)
                .clickSaveButton();
        String expected = "Please enter a part following '@'. '"+incompleteEmail+"' is incomplete.";
        String errorMsg = profilePage.getValidationMsg();
        TestListener.logInfoDetails("Expected String: " + expected);
        TestListener.logRsDetails("Actual String: " + errorMsg);
        TestListener.logAssertionDetails("Correct error message was displayed: " + expected.equalsIgnoreCase(errorMsg));
        Assert.assertEquals(expected.toLowerCase(), errorMsg.toLowerCase());
    }
    @Test(description =  "Verify form validation error message when updating email with incorrect '.' placement")
    @Parameters({"incorrectDotEmail", "password"})
    public void updateWithIncorrectDot(String incorrectDotEmail, String password){
        TestListener.logInfoDetails("String email: " + incorrectDotEmail);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
        profilePage.provideCurrentPassword(password)
                .provideEmail(incorrectDotEmail)
                .clickSaveButton();
        String expected = "'.' is used at a wrong position in";
        String errorMsg = profilePage.getValidationMsg();
        TestListener.logInfoDetails("Expected String: " + expected);
        TestListener.logRsDetails("Actual String: " + errorMsg);
        TestListener.logAssertionDetails("Correct error message was displayed: " + errorMsg.contains(expected));
        Assert.assertTrue(errorMsg.contains(expected));
    }
    @Test(description =  "Update email with valid email address, log out, try to log in with old email address", priority=5)
    @Parameters({"properEmail", "password", "oldEmail"})
    public void updateWithProperEmail(String properEmail, String password, String oldEmail){
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
            try {
                profilePage
                        .provideCurrentPassword(password)
                        .provideEmail(properEmail)
                        .clickSaveButton()
                        .clickLogout();
                TestListener.logInfoDetails("Updated  email to: " + properEmail);
                loginPage.provideEmail(oldEmail)
                        .providePassword(password)
                        .clickSubmitBtn();
                TestListener.logRsDetails("User has updated email and attempted to log in with old email: " + oldEmail);
                TestListener.logAssertionDetails("User cannot login with old email: " + loginPage.getRegistrationLink());
                Assert.assertTrue(loginPage.getRegistrationLink());
            } catch (Exception e) {
                TestListener.logExceptionDetails("There was a problem with this test: " + e);
            }
        }
    @Test(description = "Execute SQL query to verify new user info is stored correctly or updated in the Koel database", priority = 6)
    @Parameters({"properEmail"})
    public void queryDbForUpdatedEmail(String properEmail) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.getUserInfo(properEmail);
        if (rs.next()) {
            String email = rs.getString("email");
            TestListener.logRsDetails(
                    "Results: " + "\n" + "<br>" +
                            "user: " + email + "\n" + "<br>"
            );
            TestListener.logRsDetails("Result: " + email);
            TestListener.logAssertionDetails("New user data has been saved correctly in the database: " + email.equals(properEmail));
            Assert.assertEquals(email, properEmail);
        }
        KoelDbBase.closeDatabaseConnection();
    }

    @Test(description="Use updated email to log in", priority=7)
    @Parameters({"properEmail", "password"})
    public void useUpdatedEmailToLogin(String properEmail, String password) {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.provideEmail(properEmail)
                .providePassword(password)
                .clickSubmitBtn();
        TestListener.logInfoDetails("Logging in with: " + properEmail);
        TestListener.logAssertionDetails("Successfully logged in with updated email: " + homePage.getUserAvatar());
        Assert.assertTrue(homePage.getUserAvatar());
    }
    @Test(description = "reset profile", priority=8)
    @Parameters({"properEmail", "password", "oldEmail"})
    public void resetProfile(String properEmail, String password, String oldEmail) {
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        try {
            loginPage.provideEmail(properEmail)
                    .providePassword(password)
                    .clickSubmitBtn();
            homePage.clickAvatar();
                profilePage
                        .provideCurrentPassword(password)
                        .provideEmail(oldEmail)
                        .clickSaveButton();
                TestListener.logInfoDetails("Reset email to: " + oldEmail);
                TestListener.logAssertionDetails("Email was successfully reset: " + profilePage.notificationPopup());
                Assert.assertTrue(profilePage.notificationPopup());
        } catch(Exception e) {
           TestListener.logExceptionDetails("Unable to reset profile: " + e);
        }
    }
    }
