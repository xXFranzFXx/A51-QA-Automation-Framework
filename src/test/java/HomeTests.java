import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTests extends BaseTest{

    @Test
    public void addSongToPlaylist() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.searchSong("dark")
                .clickViewAllButton()
                .clickFirstSearchResult()
                .clickGreenAddToBtn()
                .clickFirstSearchResult();
        Assert.assertTrue(homePage.notificationMsg());

    }
    @Test
    public void hoverOverPlayBtn() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.hoverPlay());
    }
}
