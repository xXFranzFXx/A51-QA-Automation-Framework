package testcases;
import base.BaseTest;
import db.KoelDbBase;
import db.KoelDbActions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
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
 * As a user, I want to create an account, so that I can log in and use Koel app
 * Acceptance Criteria:
 * User should be able to create an account in app
 * Add email validation to the form(must have @ symbol, dot and @testpro.io domain). Show error message if email is not valid
 * Prevent users to use + sign before @ sign to exclude multiple users generation for the same email
 * if email already exist in DB show error message "user already registered"
 * Password should be sent to user's email box
 * User should be able to login into app after registration.
 * User data should be correctly saved in DB: email, password. Password should be encrypted.
 * All test cases should be automated (only for Automation Engineers)
 *
 * QA Note: QA will need to request Database access
 */
public class AccountCreationTests extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    RegistrationPage registrationPage;
    ResultSet rs;
    TestDataHandler testData =new TestDataHandler();
    Map<String,Object> dataMap = new HashMap<>();

    public boolean verifyData(String key1, String key2) {
        Map<String, Object> testDataInMap = testData.getTestDataInMap();
        Object dataKey1 = testDataInMap.get(key1);
        Object dataKey2 = testDataInMap.get(key2);
        System.out.println("dataKey1 " + dataKey1);
        System.out.println("dataKey2 " + dataKey2);
        return dataKey1.equals(dataKey2);
    }
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
    @AfterClass
    public void clearStoredData() {
        clearDataFromTest();
    }
    @BeforeMethod
    @Parameters({"registrationURL"})
    public void setup(String registrationURL) throws MalformedURLException {
        setupBrowser(registrationURL);
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }


    @Test(description = "User can register for a new account", groups = {"Account Creation"})
    @Parameters({"koelNewUser"})
    public void register(String koelNewUser) {
        TestListener.logInfoDetails("String koelNewUser: " + koelNewUser);
        registrationPage = new RegistrationPage(getDriver());
        registrationPage.provideEmail(koelNewUser)
                .clickSubmit();
        TestListener.logRsDetails("Confirmation Message: " + registrationPage.confirmationMsgText());
        TestListener.logAssertionDetails("Confirmation Message is displayed: " + registrationPage.getConfirmationMsg());
        Assert.assertTrue(registrationPage.getConfirmationMsg());
    }

    @Test(description =  "Verify form validation messages when incorrectly formatted email used to register an account", groups = {"Account Creation"})
    @Parameters({"email"})
    public void regWithIncorrectEmailFmt(String email){
        TestListener.logInfoDetails("String email: " + email);
        registrationPage = new RegistrationPage(getDriver());
        registrationPage.provideEmail(email)
                .clickSubmit();
        String expected = "Please include an '@' in the email address. '"+email+"' is missing an '@'.";
        String validationMsg = registrationPage.getValidationMsg();
        TestListener.logInfoDetails("Expected String: " + expected);
        TestListener.logRsDetails("Actual String: " + validationMsg);
        TestListener.logAssertionDetails("Expected String equals Actual String: " + expected.equals(validationMsg));

        Assert.assertEquals(expected, validationMsg);
    }
    @Test(description = "Verify user can log in with new account", groups = {"Account Creation"})
    @Parameters({"baseURL", "koelNewUser", "password"})
    public void verifyLogin(String baseURL, String koelNewUser, String password) {
        TestListener.logInfoDetails("String koelNewUser: " + koelNewUser);
        driver.get(baseURL);
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.provideEmail(koelNewUser)
                .providePassword(password)
                .clickSubmitBtn();
        TestListener.logAssertionDetails("Locate user avatar after logging in: " + homePage.getUserAvatar());
        Assert.assertTrue(homePage.getUserAvatar());

    }
    @Test(description = "Execute SQL query to verify new user info is stored correctly or updated in the Koel database", groups = {"Account Creation"}, priority=3)
    @Parameters({"koelNewUser", "password"})
    public void queryDbForNewUser(String koelNewUser, String password) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.getUserInfo(koelNewUser);
        if (rs.next()) {
            String ep = rs.getString("password");
            String updated = rs.getString("updated_at");
            String email = rs.getString("email");
            TestListener.logRsDetails(
                    "Results: " +"\n" +"<br>"+
                            "encrypted password: " + ep +"\n" +"<br>"+
                            "updated_at: " + updated +"\n" +"<br>"+
                            "user: " + email +"\n" +"<br>"
            );


            addDataFromTest("existingUser", email);   //store the account email to use for the next test
            TestListener.logAssertionDetails("New user data has been saved correctly in the database: " + email.equals(koelNewUser));
            Assert.assertNotSame(ep, password);
            Assert.assertEquals(email, koelNewUser);
        }

        KoelDbBase.closeDatabaseConnection();
    }
    //use the account from the previous db query
    @Test(description = "Get existing user from database, attempt to register with that account", groups = {"Account Creation"}, priority=4)
    public void tryRegisteringExistingUser() {
        String existingUser = getDataValue("existingUser").toString(); //this value comes from the previous db query
        TestListener.logInfoDetails("Existing Account : " + existingUser);
        registrationPage = new RegistrationPage(getDriver());
        registrationPage.provideEmail(existingUser)
                .clickSubmit();
        TestListener.logRsDetails("Confirmation Message: " + registrationPage.confirmationMsgText());
        TestListener.logAssertionDetails("Confirmation Message is displayed: " + registrationPage.getConfirmationMsg());
        Assert.assertTrue(registrationPage.getConfirmationMsg());

    }
    @Test(description = "Try registering with personal email account", groups = {"Account Creation"}, priority=4)
    public void tryRegisteringPersonalEmail() {
        String personalEmail = "ff@hotmail.com";
        String expectedErrorMsg = "Sorry, only certain emails are allowed, please do not use your personal email";
        TestListener.logInfoDetails("Personal email : " + personalEmail);
        registrationPage = new RegistrationPage(getDriver());
        registrationPage.provideEmail(personalEmail)
                .clickSubmit();
        TestListener.logInfoDetails("Expected Error Message: " + expectedErrorMsg);
        TestListener.logRsDetails("Error Message: " + registrationPage.getPersonalEmailMsg());
        TestListener.logAssertionDetails("Expected Error matches Error Message: " + expectedErrorMsg.equalsIgnoreCase(registrationPage.getPersonalEmailMsg()));
        Assert.assertEquals(expectedErrorMsg.toLowerCase(), registrationPage.getPersonalEmailMsg().toLowerCase());

    }

}
