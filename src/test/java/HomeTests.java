import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTests extends BaseTest {


    @Test(description = "Add a song to a playlist")
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
        Reporter.log("Added song to playlist", true);

    }
    @Test
    public void hoverOverPlayBtn() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.hoverPlay());
    }

    @Test
    public void recentlyPlayedExists() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.recentlyPlayedListExists());
        Reporter.log("Recently Played song list size is:" + homePage.recentlyPlayedListSize(), true);

    }
    @Test
    public void checkRecentlyAdded() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.recentlyAddedListHasAlbumTitles());

    }
    @Test
    public void checkRASongsOnHover() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.checkRAListButtonsOnHover());

    }
    @Test
    public void clickAboutLink() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAboutLink();
        Assert.assertTrue(homePage.aboutModalVisible());
    }
    @Test
    public void modalCloses() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickAboutLink()
                .closeModalPopup();
        Assert.assertTrue(homePage.isModalClosed());
    }

}
