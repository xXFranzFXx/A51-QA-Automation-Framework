package testcases;

import base.BaseTest;
import db.KoelDb;
import db.KoelDbActions;
import org.apache.poi.ss.formula.functions.T;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    @BeforeMethod
    @Parameters({"registrationURL"})
    public void setup(String registrationURL) throws MalformedURLException {
        setupBrowser(registrationURL);
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }

    @Test(description = "User can register for a new account")
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
    @Test(description =  "Verify form validation messages when incorrectly formatted email used to register an account")
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
    @Test(description = "Verify user can log in with new account")
    @Parameters({"baseURL", "koelNewUser", "password"})
    public void verifyLogin(String baseURL, String koelNewUser, String password) {
        TestListener.logInfoDetails("String koelNewUser: " + koelNewUser);
        driver.get(baseURL);
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage.provideEmail(koelNewUser)
                .providePassword(password)
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }
    @Test(description = "Execute SQL query to verify password is encrypted and has been updated in the Koel database", priority=3)
    @Parameters({"koelNewUser", "password"})
    public void queryDbForNewUser(String koelNewUser, String password) throws SQLException, ClassNotFoundException {
        KoelDb.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.getUserInfo(koelNewUser);
        if (rs.next()) {
            String ep = rs.getString("password");
            String updated = rs.getString("updated_at");
            String email = rs.getString("email");
            TestListener.logPassDetails(
                    "Results: " +"\n" +"<br>"+
                            "encrypted password: " + ep +"\n" +"<br>"+
                            "updated_at: " + updated +"\n" +"<br>"+
                            "user: " + email +"\n" +"<br>"
            );
            TestListener.logAssertionDetails("Assertions: " +koelNewUser+ " equals " + email);
            Assert.assertNotSame(ep, password);
            Assert.assertEquals(email, koelNewUser);
        }
        Assert.assertFalse(false);
        KoelDb.closeDatabaseConnection();
    }

}
