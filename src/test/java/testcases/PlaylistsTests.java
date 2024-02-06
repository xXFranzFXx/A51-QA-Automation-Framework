package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import util.DataProviderUtil;
import util.RandomString;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * INTERNSHIP-82853
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
    private final int maxLength = 256;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        setupBrowser(System.getProperty("baseURL"));
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }
    @AfterClass
    public void closeDb() throws SQLException {
        KoelDbBase.closeDatabaseConnection();
    }
    private boolean checkDatabaseForPlaylist(String koelUser, String playlistName) throws SQLException, ClassNotFoundException {
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
    private boolean checkDatabaseForSongInPlaylist(String koelUser, String song) throws SQLException, ClassNotFoundException {
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
    public boolean duplicateCondition (int duplicates) {
        return duplicates >= 2;
    }
    private int countDuplicateNames(String koelUser, String playlistName) throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        int duplicates = 0;
        rs = koelDbActions.checkDuplicatePlaylistNames(koelUser, playlistName);
        if (rs.next()) {
            duplicates = rs.getInt("count");
            TestListener.logInfoDetails("Total playlists with same name: " + duplicates);
            TestListener.logAssertionDetails("User can create playlists with duplicate names: " + duplicateCondition(duplicates));
        }
      return duplicates;
    }
    private String generatePlaylistName(int length) {
       return  RandomString.getAlphaNumericString(length);
    }
    @Test(description = "User can create a playlist", dataProvider="PlaylistData", dataProviderClass = DataProviderUtil.class)
    public void createPlaylist(String playlist) {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlist);
        Assert.assertTrue(homePage.playlistAddedToMenu(playlist));
    }
    @Test(description = "Verify user can create playlists with duplicate names, check database for multiple playlists with same name", dataProvider="PlaylistData", dataProviderClass = DataProviderUtil.class, dependsOnMethods = {"createPlaylist"})
    public void createPlaylistDuplicateName(String playlist) throws SQLException, ClassNotFoundException {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlist);
        int duplicateCount = countDuplicateNames(System.getProperty("koelUser"), playlist);
        Assert.assertTrue(duplicateCondition(duplicateCount), "No duplicate playlist names were found");
    }
    @Test(description = "Add a song to a playlist", dependsOnMethods = {"createPlaylist"})
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

    @Test(description = "delete all playlists",dependsOnMethods = {"addSongToPlaylist"})
    public void deleteAllPlaylists() throws TimeoutException {
        homePage = new HomePage(getDriver());
        homePage.deleteAllPlaylists();
        Assert.assertTrue(homePage.playlistsEmpty(), "Could not delete all playlists");
    }
    @Test(description = "Create a playlist with over 256 characters and check if it is saved to the database")
    public void createLongPlaylistName() throws SQLException, ClassNotFoundException {
        String playlistName = generatePlaylistName(maxLength);
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
    @Test(description = "Create a playlist with a name containing one character and verify it is in the database", dataProvider = "PlaylistData", dataProviderClass = DataProviderUtil.class)
    public void createOneCharacterPlaylistName(String playlistName) throws SQLException, ClassNotFoundException {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewPlaylist()
                .enterPlaylistName(playlistName);
        Assert.assertTrue(checkDatabaseForPlaylist(System.getProperty("koelUser"), playlistName), "Playlist not found in database");
        Assert.assertTrue(homePage.playlistAddedToMenu(playlistName), "Playlist was not successfully created");
    }
}
