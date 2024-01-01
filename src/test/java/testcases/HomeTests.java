package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static util.extentReports.ExtentTestManager.startTest;

public class HomeTests extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    public HomeTests() {
        super();
    }

    @BeforeClass
    public void setEnv() {
        loadEnv();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }


    @Test(description = "User can create a playlist", priority = 1)
    public void createPlaylist() {
//        startTest(method.getName(), method.toGenericString());
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("playlist");

        Assert.assertTrue(homePage.playlistAddedToMenu("playlist"));

    }
    @Test(description = "Add a song to a playlist", priority = 2, dependsOnMethods = {"createPlaylist"})
    public void addSongToPlaylist() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        homePage.searchSong("dark")
                .clickViewAllButton()
                .clickFirstSearchResult()
                .clickGreenAddToBtn()
                .selectPlaylistToAddTo();
        Assert.assertTrue(homePage.notificationMsg());
        Reporter.log("Added song to playlist", true);

    }
    @Test
    public void hoverOverPlayBtn() throws InterruptedException {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.hoverPlay());
    }

    @Test
    public void recentlyPlayedExists() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.recentlyPlayedListExists());
        Reporter.log("Recently Played song list size is:" + homePage.recentlyPlayedListSize(), true);

    }
    @Test
    public void checkRecentlyAdded() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.recentlyAddedListHasAlbumTitles());

    }
    @Test
    public void checkRASongsOnHover() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.checkRAListButtonsOnHover());

    }
    @Test
    public void clickAboutLink() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        homePage.clickAboutLink();
        Assert.assertTrue(homePage.aboutModalVisible());
    }
    @Test
    public void modalCloses() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();

        homePage = new HomePage(getDriver());
        homePage.clickAboutLink()
                .closeModalAndLogOut();

    }
//  @AfterClass(description = "delete playlist created", dependsOnMethods = { "createPlaylist" })
    public void deletePlaylist() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        homePage = new HomePage(getDriver());
        homePage.contextClickFirstPlDelete();
        Assert.assertTrue(homePage.notificationMsg());
    }
//    @AfterClass
    @Test(priority = 3)
    public void deleteAllPlaylists() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        homePage = new HomePage(getDriver());
        homePage.deleteAllPlaylists();
        Assert.assertTrue(homePage.playlistsEmpty());
    }

}
