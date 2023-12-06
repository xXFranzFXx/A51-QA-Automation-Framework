import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {


    @Test
    public void loginSuccessTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent")
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
        loginPage.getRegistrationLink();
    }

    @Test
    public void loginWrongEmailTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("wrong@wrong.mail")
                .providePassword("te$t$tudent")
                .clickSubmitBtn();
        loginPage.getRegistrationLink();
    }

    @Test
    public void loginEmptyPasswordTest() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("")
                .clickSubmitBtn();
        loginPage.getRegistrationLink();
    }
    @Test(dataProvider = "LoginData")
    public void loginWithLoginData(String email, String password) {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =  new LoginPage(getDriver());
        loginPage.provideEmail(email)
                .providePassword(password)
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
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
        } catch(Exception e){
            Reporter.log("Unable to login with Excel Data for an unknown reason." + e);
        }
    }
}
