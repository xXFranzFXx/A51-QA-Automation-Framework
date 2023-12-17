package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.*;

import java.net.MalformedURLException;

public class HomeTests extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    public HomeTests() {
        super();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        homePage = loginPage.loginValidCredentials();
    }

    @Test(description = "User can create a playlist")
    public void createPlaylist() {
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("playlist");
        Assert.assertTrue(homePage.playlistAddedToMenu("playlist"));

    }
    @Test(description = "Add a song to a playlist", dependsOnMethods = { "createPlaylist" })
    public void addSongToPlaylist() {
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
        Assert.assertTrue(homePage.hoverPlay());
    }

    @Test
    public void recentlyPlayedExists() {
        Assert.assertTrue(homePage.recentlyPlayedListExists());
        Reporter.log("Recently Played song list size is:" + homePage.recentlyPlayedListSize(), true);

    }
    @Test
    public void checkRecentlyAdded() {
        Assert.assertTrue(homePage.recentlyAddedListHasAlbumTitles());

    }
    @Test
    public void checkRASongsOnHover() {
        Assert.assertTrue(homePage.checkRAListButtonsOnHover());

    }
    @Test
    public void clickAboutLink() {
        homePage.clickAboutLink();
        Assert.assertTrue(homePage.aboutModalVisible());
    }
    @Test
    public void modalCloses() {
        homePage.clickAboutLink()
                .closeModalAndLogOut();
    }
//  @AfterClass(description = "delete playlist created", dependsOnMethods = { "createPlaylist" })
    public void deletePlaylist() {
        homePage.contextClickFirstPlDelete();
        Assert.assertTrue(homePage.notificationMsg());
    }
//    @AfterClass
    public void deleteAllPlaylists() {
        homePage.deleteAllPlaylists();
    }

}
