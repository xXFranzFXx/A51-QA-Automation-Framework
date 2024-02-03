package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AllSongsPage;
import pages.HomePage;
import pages.LoginPage;
import util.RandomString;
import util.TestDataHandler;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Story:
 * As a user, I want to add songs to the playlist in-app, so that I can enjoy the music
 * Acceptance Criteria:
 * User should be able to create a New playlist in-app
 * User should be able to add songs to the New playlist
 * User’s playlist should be updated correctly in DB
 * 256 characters is max for a playlist name, 1 character is min(users should not be able to create playlists with empty names)
 */
public class PlaylistsTests extends BaseTest {
    LoginPage loginPage;
    AllSongsPage allSongsPage;
    HomePage homePage;
    ResultSet rs;

    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }


    @Test(description = "User can create a playlist", priority = 1)
    public void createPlaylist() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("playlist");
        Assert.assertTrue(homePage.playlistAddedToMenu("playlist"));
    }
    @Test(description = "Add a song to a playlist", priority = 2, dependsOnMethods = {"createPlaylist"})
    public void addSongToPlaylist() {
        homePage = new HomePage(getDriver());
        homePage.searchSong("dark")
                .clickViewAllButton()
                .clickFirstSearchResult()
                .clickGreenAddToBtn()
                .selectPlaylistToAddTo();
        Assert.assertTrue(homePage.notificationMsg());
        Reporter.log("Added song to playlist", true);
    }
    @Test(description = "get a user's playlists and write the data from the result set to excel file")
    @Parameters({"koelUser"})
    public void getKoelUserPlaylists(String koelUser) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.getUserPlaylst(koelUser);
        if(rs.next()) {
            String p_uid = rs.getString("p.user_id");
            String u_id = rs.getString("u.id");
            String email = rs.getString("email");
            addDataFromTest("getKoelUserPlaylists", rs);
            Assert.assertEquals(p_uid, u_id);
        }
        Assert.assertFalse(false);
    }
    @Test(description = "delete all playlists", priority=3)
    public void deleteAllPlaylists() {
        homePage = new HomePage(getDriver());
        homePage.deleteAllPlaylists();
        Assert.assertTrue(homePage.playlistsEmpty());
    }
    @Test(description = "Create a playlist with over 256 characters")
    public void createLongPlaylistName() {
        String playlistName = RandomString.getAlphaNumericString(256);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlistName);
        Assert.assertTrue(homePage.playlistAddedToMenu(playlistName));
    }
    @Test(description = "Create a playlist with blank name")
    public void createBlankPlaylistName() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("");
        String actualMsg = homePage.getSearchInputValidationMsg();
        String expected = "Please fill out this field.";
        Assert.assertTrue(expected.equalsIgnoreCase(actualMsg));
    }
    @Test(description = "Create a playlist with a name containing one character")
    public void createOneCharacterPlaylistName() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("a");
        Assert.assertTrue(homePage.notificationMsg());
    }
}
