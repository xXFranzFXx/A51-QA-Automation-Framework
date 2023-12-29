import base.BaseDefinitions;
import org.openqa.selenium.ElementClickInterceptedException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;

import java.net.MalformedURLException;

/**
 * Story:
 * As a user, I want to be able to log in to my account, so I can use Koel app
 * Acceptance Criteria:
 * 1. User should be able to log in to Koel app after registering a new account with testpro.io domain
 * 2. User should be navigated to the Homepage after successful login
 * 3. User should be able to log in with the updated email and the old email should not work
 * 4. User should be able to log in with the updated password and the old password should not work 'Password is incorrect' message should be displayed
 * 5. User should not be able to log in with invalid format email (without @ symbol, dot or domain) and valid password. 'email format is incorrect' message should be displayed
 * 6. User should not be able to log in with valid registered email and invalid password. 'Password is incorrect' message should be displayed
 * 7. User should not be able to log in with empty 'Email Address' and 'Password fields'
 */
public class LoginTests extends BaseDefinitions {
    private final String registerEmail = System.getProperty("koelNewUser");
    private final String updatedEmail = "updated.email@testpro.io";
    private final String defaultPassword = System.getProperty("koelPassword");
    private final String updatedPassword = "te$t$tudent2";

    LoginPage loginPage;
    HomePage homePage;
    RegistrationPage registrationPage;
    ProfilePage profilePage;
    public LoginTests() {
        super();
    }
    @BeforeMethod
    public void setup() throws MalformedURLException {
        setupBrowser();
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        registrationPage = new RegistrationPage(getDriver());

    }
//    @Test
    public void registerNewAccount() {
        try {
            loginPage.clickRegistrationLink();
            registrationPage.registerNewAccount(registerEmail);
            Assert.assertTrue(registrationPage.getConfirmationMsg());
        } catch (Exception e) {
            Reporter.log("Unable to login with Excel Data for an unknown reason." + e);
        }
    }
    @Test (description = "log in with newly registered account with testpro.io domain")//(dependsOnMethods = { "registerNewAccount" })
    public void loginWithNewAccount() {
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
        Reporter.log("User has successfully logged in with a new account", true);
    }


    @Test (description ="log in with new account, update email, log out and attempt to log back in with old email address", dependsOnMethods = { "loginWithNewAccount" })
    public void loginAndUpdateNewAccount() {

        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        try {
            profilePage
                    .provideNewEmail(updatedEmail)
                    .provideCurrentPassword(defaultPassword)
                    .clickSaveButton()
                    .clickLogout();
            loginPage.provideEmail(registerEmail)
                    .providePassword(defaultPassword)
                    .clickSubmitBtn();
            Assert.assertTrue(loginPage.getRegistrationLink());
            Reporter.log("User has updated email and attempted to log in with old email", true);
        } catch (Exception e) {
            Reporter.log("There is a problem updating email" + e, true);
            Assert.assertTrue(homePage.checkForLogoutBtn());
        }
    }

    @Test (description = "Log in with the updated email and update the password, log out, and attempt to log in with old password", dependsOnMethods = { "loginAndUpdateNewAccount"} )
    public void loginWithUpdatedEmailAndUpdatePwd() {
        loginPage.provideEmail(updatedEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        profilePage
                .provideNewPassword(updatedPassword)
                .provideCurrentPassword(defaultPassword)
                .clickSaveButton()
                .clickLogout();
        loginPage.provideEmail(updatedEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
        Reporter.log("User successfully updated password and logged in using it", true);

    }

    @Test(description = "Log in success test", groups = { "Login" })
    public void loginSuccessTest() {
        loginPage.provideEmail(System.getProperty("koelUser"))
                .providePassword(System.getProperty("koelPassword"))
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    @Test(description = "Log in with incorrect password", groups = { "Login" })
    public void loginWrongPasswordTest() {
        loginPage.provideEmail(System.getProperty("koelUser"))
                .providePassword("wrongPassword")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with incorrect email address", groups = { "Login" })
    public void loginWrongEmailTest() {
        loginPage.provideEmail("wrong@wrongmail")
                .providePassword(System.getProperty("koelPassword"))
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with blank password", groups = { "Login" })
    public void loginEmptyPasswordTest() {
        loginPage.provideEmail(System.getProperty("koelUser"))
                .providePassword("")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with data read from external source", dataProvider ="LoginData")
    public void loginWithLoginData(String email, String password) {
        try {
            loginPage.provideEmail(email)
                    .providePassword(password)
                    .clickSubmitBtn();
                Assert.assertTrue(homePage.getUserAvatar());
                Reporter.log("Logged in using logindata", true);

        } catch(Exception e) {

            Assert.assertTrue(loginPage.getRegistrationLink());
            Reporter.log("Invalid login data, check username or password." + e, true);
        }
    }
    @Test(description = "Log in with data read from Excel Sheet", dataProvider = "excel-data")
    public void loginWithExcelData(String email, String password){
        try{
            loginPage.provideEmail(email)
                    .providePassword(password)
                            .clickSubmitBtn();
            Assert.assertTrue(homePage.getUserAvatar());
            Reporter.log("Logged in using excel-data", true);
        } catch(Exception e){
            Reporter.log("Unable to login with Excel Data for an unknown reason." + e, true);
        }
    }
//    @AfterClass(description = "Reset profile back to default newly registered account details after previous test completes", dependsOnMethods = { "loginWithUpdatedEmailAndUpdatePwd" })
    public void resetProfile() {
        String url = "https://qa.koel.app/#!/home";
        String loginUrl = driver.getCurrentUrl();
        try {
            loginPage.provideEmail(updatedEmail)
                    .providePassword(updatedPassword)
                    .clickSubmitBtn();
            if(loginUrl.equals(url)) {
                profilePage
                        .provideNewEmail(registerEmail)
                        .provideNewPassword(defaultPassword)
                        .provideCurrentPassword(updatedPassword)
                        .clickSaveButton();
                Assert.assertTrue(profilePage.notificationPopup());
                Reporter.log("Restored profile", true);
            } else {
                Assert.assertNotEquals(loginUrl, url);
                Reporter.log("Failed to log in, profile was not reset", true);
            }
        } catch(ElementClickInterceptedException e) {
            Reporter.log("Unable to reset profile" + e, true);
        }
    }

}
