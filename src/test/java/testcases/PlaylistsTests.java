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
import util.TestUtil;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    HomePage homePage;
    ResultSet rs;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        setupBrowser(System.getProperty("baseURL"));
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }
    public boolean checkDatabaseForPlaylist(String koelUser, String playlistName) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.checkNewPlaylist(koelUser, playlistName);
        if(rs.next()) {
            String playlist = rs.getString("p.name");
            TestListener.logInfoDetails("Playlist in database: " + playlist);
            TestListener.logAssertionDetails("Created playlist exists in database: " + playlistName.equalsIgnoreCase(playlist));
            return playlistName.equalsIgnoreCase(playlist);
        }
        return false;
    }
    public boolean checkDatabaseForSongInPlaylist(String koelUser, String song) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.checkSongsInPlaylist(koelUser);
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        final int columnCount = resultSetMetaData.getColumnCount();
        boolean found = false;
        TestListener.logInfoDetails("Searching for song containing the word '"+song+"' ");
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String dbSong = rs.getString(i).toLowerCase();
                found = dbSong.contains(song);
                TestListener.logInfoDetails("Playlist song found: " + dbSong);
                TestListener.logAssertionDetails("Song added to playlist matches playlist song found in database: " + dbSong.contains(song));
                if (found) break;
            }
        }
        return found;
    }
    @Test(description = "User can create a playlist", priority = 1)
    public void createPlaylist() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("playlist");
        Assert.assertTrue(homePage.playlistAddedToMenu("playlist"));
    }
    @Test(description = "Add a song to a playlist")//, priority = 2, dependsOnMethods = {"createPlaylist"})
    @Parameters({"song"})
    public void addSongToPlaylist(String song) throws SQLException, ClassNotFoundException {
        homePage = new HomePage(getDriver());
        homePage.searchSong(song)
                .clickViewAllButton()
                .clickFirstSearchResult()
                .clickGreenAddToBtn()
                .selectPlaylistToAddTo();
        Assert.assertTrue(checkDatabaseForSongInPlaylist(System.getProperty("koelUser"), song), "Playlist song could not be found");
    }

    @Test(description = "delete all playlists", priority=3)
    public void deleteAllPlaylists() {
        homePage = new HomePage(getDriver());
        homePage.deleteAllPlaylists();
        Assert.assertTrue(homePage.playlistsEmpty(), "Could not delete all playlists");
    }
    @Test(description = "Create a playlist with over 256 characters and check if it is saved to the database")
    public void createLongPlaylistName()    throws SQLException, ClassNotFoundException {
        String playlistName = RandomString.getAlphaNumericString(256);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlistName);
        Assert.assertTrue(checkDatabaseForPlaylist(System.getProperty("koelUser"), playlistName), "Playlist not found in database");
        Assert.assertTrue(homePage.playlistAddedToMenu(playlistName), "Playlist was not successfully created");
    }
    @Test(description = "Verify user cannot create a playlist with blank name")
    public void createBlankPlaylistName() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName("");
        String actualMsg = homePage.getSearchInputValidationMsg();
        String expected = "Please fill out this field.";
        TestListener.logInfoDetails("Form validation message: " + actualMsg);
        TestListener.logAssertionDetails("User cannot create playlist with blank name: " + !homePage.getSearchInputValidationMsg().isBlank());
        Assert.assertTrue(expected.equalsIgnoreCase(actualMsg));
    }
    @Test(description = "Create a playlist with a name containing one character and verify it is in the database")
    public void createOneCharacterPlaylistName() throws SQLException, ClassNotFoundException {
        String playlistName = "a";
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlistName);
        Assert.assertTrue(checkDatabaseForPlaylist(System.getProperty("koelUser"), playlistName), "Playlist not found in database");
        Assert.assertTrue(homePage.playlistAddedToMenu(playlistName), "Playlist was not successfully created");
    }
}
