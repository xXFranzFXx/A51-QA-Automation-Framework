import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomePageTests extends BaseTest{
    LoginPage loginPage = new LoginPage(getDriver());
    HomePage homePage = new HomePage(getDriver());
    @Test
    public void addSongToPlaylist() {
        loginPage.loginValidCredentials();

    }
}
