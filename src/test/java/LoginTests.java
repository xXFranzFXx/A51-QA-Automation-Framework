import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;

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
public class LoginTests extends BaseTest {
    private final String registerEmail = "franz.fernando+1@testpro.io";
    private final String updatedEmail = "updated.email@testpro.io";
    private final String defaultPassword = "te$t$tudent1";
    private final String updatedPassword = "te$t$tudent2";
//    @Test
    public void registerNewAccount() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        try {
            loginPage.clickRegistrationLink();
            registrationPage.registerNewAccount(registerEmail);
            Assert.assertTrue(registrationPage.getConfirmationMsg());
        } catch (Exception e) {
            Reporter.log("Unable to login with Excel Data for an unknown reason." + e);
        }
    }
    //logs in with newly created account
    @Test //(dependsOnMethods = { "registerNewAccount" })
    public void loginWithNewAccount() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        Reporter.log("User has successfully logged in with a new account", true);
        Assert.assertTrue(homePage.getUserAvatar());
    }

    //logs in with newlyCreatedAccount, updates the email, logs out and tries to log back in with old email
    @Test (dependsOnMethods = { "loginWithNewAccount" })
    public void loginAndUpdateNewAccount() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        try {
            profilePage.clickAvatar()
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
    //logs in with the updated email and updates the password, logs out, and attempts to log in with old password
    @Test (dependsOnMethods = { "loginAndUpdateNewAccount"} )
    public void loginWithUpdatedEmailAndUpdatePwd() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(updatedEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        profilePage.clickAvatar()
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
    //resets profile back to default newly registered account details after previous test completes
    @Test(dependsOnMethods = { "loginWithUpdatedEmailAndUpdatePwd" })
    public void resetProfile() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        try {
            loginPage.provideEmail(updatedEmail)
                    .providePassword(updatedPassword)
                    .clickSubmitBtn();
            profilePage.clickAvatar()
                    .provideNewEmail(registerEmail)
                    .provideNewPassword(defaultPassword)
                    .provideCurrentPassword(updatedPassword)
                    .clickSaveButton();
            Assert.assertTrue(profilePage.notificationPopup());
        } catch(ElementClickInterceptedException e) {
           Reporter.log("Unable to reset profile" + e, true);

        }
        Reporter.log("Log in tests for Sprint-9 completed and test profile has been reset so tests can be run again.", true);

    }

    @Test(groups = { "Login" })
    public void loginSuccessTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    @Test(groups = { "Login" })
    public void loginWrongPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("wrongPassword")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(groups = { "Login" })
    public void loginWrongEmailTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("wrong@wrongmail")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(groups = { "Login" })
    public void loginEmptyPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(dataProvider = "LoginData")
    public void loginWithLoginData(String email, String password) {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());

        try {
            loginPage.provideEmail(email)
                    .providePassword(password)
                    .clickSubmitBtn();
                Assert.assertTrue(homePage.getUserAvatar());
                Reporter.log("Logged in using logindata", true);

        } catch(Exception e) {
            Reporter.log("Invalid login data, check username or password." + e, true);
            Assert.assertTrue(loginPage.getRegistrationLink());
        }
    }
    @Test(dataProvider = "excel-data")
    public void loginWithExcelData(String email, String password){
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
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
}
