package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;
import util.TestDataHandler;
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
    @Test(description = "Execute SQL query to verify new user info is stored correctly or updated in the Koel database")
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
            TestListener.logAssertionDetails("New user data has been saved correctly in the database: " + email.equals(koelExistingUser));
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
    @Test(description =  "Verify form validation error message when updating email with incorrect '.' placement")
    @Parameters({"updatedEmail", "password"})
    public void updateWithProperEmail(String updatedEmail, String password){
        TestListener.logInfoDetails("String email: " + updatedEmail);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAvatar();
        profilePage.provideCurrentPassword(password)
                .provideEmail(updatedEmail)
                .clickSaveButton();
        
    }
}
