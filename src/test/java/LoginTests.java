import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

public class LoginTests extends BaseTest {
    LoginPage loginPage =  new LoginPage(getDriver());
    HomePage homePage = new HomePage(getDriver());
    @Test
    public void loginSuccessTest() {
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent")
                .clickSubmitBtn();
        Assert.assertTrue(homePage.getUserAvatar());
    }

    @Test
    public void loginWrongPasswordTest() {
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("wrongPassword")
                .clickSubmitBtn();
        loginPage.getRegistrationLink();
    }

    @Test
    public void loginWrongEmailTest() {
        loginPage.provideEmail("wrong@wrong.mail")
                .providePassword("te$t$tudent")
                .clickSubmitBtn();
        loginPage.getRegistrationLink();
    }

    @Test
    public void loginEmptyPasswordTest() {
        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("")
                .clickSubmitBtn();
        loginPage.getRegistrationLink();
    }


}
