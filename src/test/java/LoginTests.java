import org.openqa.selenium.ElementClickInterceptedException;
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
    @Test (description = "log in with newly registered account with testpro.io domain")//(dependsOnMethods = { "registerNewAccount" })
    public void loginWithNewAccount() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        Reporter.log("User has successfully logged in with a new account", true);
        Assert.assertTrue(homePage.getUserAvatar());
    }


    @Test (description ="log in with new account, update email, log out and attempt to log back in with old email address", dependsOnMethods = { "loginWithNewAccount" })
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

    @Test (description = "Log in with the updated email and update the password, log out, and attempt to log in with old password", dependsOnMethods = { "loginAndUpdateNewAccount"} )
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
    //
    @Test(description = "Reset profile back to default newly registered account details after previous test completes", dependsOnMethods = { "loginWithUpdatedEmailAndUpdatePwd" })
    public void resetProfile() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        String url = "https://qa.koel.app/#!/home";
        String loginUrl = driver.getCurrentUrl();
        try {
            loginPage.provideEmail(updatedEmail)
                    .providePassword(updatedPassword)
                    .clickSubmitBtn();
            if(loginUrl.equals(url)) {
                profilePage.clickAvatar()
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

    @Test(description = "Log in success test", groups = { "Login" })
    public void loginSuccessTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    @Test(description = "Log in with incorrect password", groups = { "Login" })
    public void loginWrongPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("wrongPassword")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with incorrect email address", groups = { "Login" })
    public void loginWrongEmailTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("wrong@wrongmail")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with blank password", groups = { "Login" })
    public void loginEmptyPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test(description = "Log in with data read from external source", dataProvider = "LoginData")
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

            Assert.assertTrue(loginPage.getRegistrationLink());
            Reporter.log("Invalid login data, check username or password." + e, true);
        }
    }
    @Test(description = "Log in with data read from Excel Sheet", dataProvider = "excel-data")
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
