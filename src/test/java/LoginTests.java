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
    private String registerEmail = "new.testaccounttestpro.io";
    private String updatedEmail = "updated.email@testpro.io";
    private String defaultPassword = "te$t$tudent";
    private String updatedPassword = "te$t$tudent2";
//    @Test
    public void registerNewAccount(){
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        RegistrationPage registrationPage = new RegistrationPage(getDriver());

        loginPage.clickRegistrationLink();
        registrationPage.registerNewAccount(registerEmail);
        Assert.assertTrue(registrationPage.getConfirmationMsg());
    }
//    @Test
    public void loginWithNewAccount() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(registerEmail)
                .providePassword("te$t$tudent")
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    //logs in with newlyCreatedAccount, updates the email, logs out and tries to log back in with old email
//    @Test
    public void loginAndUpdateNewAccount() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        profilePage.clickAvatar()
                .provideNewEmail(updatedEmail)
                .provideCurrentPassword(defaultPassword)
                .clickSaveButton()
                .clickLogout();
        loginPage.provideEmail(registerEmail)
                .providePassword(defaultPassword)
                .clickSubmitBtn();
        Assert.assertFalse(homePage.getUserAvatar());

    }

    @Test
    public void loginSuccessTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    @Test
    public void loginWrongPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("wrongPassword")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test
    public void loginWrongEmailTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("wrong@wrongmail")
                .providePassword("te$t$tudent1")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Test
    public void loginEmptyPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("")
                .clickSubmitBtn();
        Assert.assertTrue(loginPage.getRegistrationLink());
    }
//    @Test(dataProvider = "LoginData")
    public void loginWithLoginData(String email, String password) {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(email)
                .providePassword(password)
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }
//    @Test(dataProvider = "excel-data")
    public void loginWithExcelData(String email, String password){
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        try{
            loginPage.provideEmail(email)
                    .providePassword(password)
                            .clickSubmitBtn();
            Assert.assertTrue(homePage.getUserAvatar());
        } catch(Exception e){
            Reporter.log("Unable to login with Excel Data for an unknown reason." + e);
        }
    }
}
