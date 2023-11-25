import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTests extends BaseTest{
    LoginPage loginPage = new LoginPage(getDriver());
    HomePage homePage = new HomePage(getDriver());
    @Test
    public void addSongToPlaylist() {
        loginPage.loginValidCredentials();

    }
    @Test
    public void hoverOverPlayBtn() throws InterruptedException {
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.hoverPlay());
    }
}
