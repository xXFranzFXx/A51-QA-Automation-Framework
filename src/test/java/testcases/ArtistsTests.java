package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ArtistsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CLONED BY INTERNSHIP-83449
 * Story:
 * As a user, I want to have multiple artists in app, so that I can enjoy different music
 * Acceptance Criteria:
 * User should be able to see artists in app
 * User should be able to find artists using search field
 * User should be able to play a song of selected artist
 * Artists should be displayed correctly in DB
 */
public class ArtistsTests extends BaseTest {
    LoginPage loginPage;
    ArtistsPage artistsPage;
    SearchPage searchPage;
    ResultSet rs;
    List<String> dataList = new ArrayList<>();
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setUp(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        artistsPage = new ArtistsPage(getDriver());
        searchPage = new SearchPage(getDriver());
        loginPage.loginValidCredentials();
        artistsPage.navigateToArtistsPage();
    }
    @Test(description = "Verify artists are displayed")
    public void artistsAreDisplayed() {
        TestListener.logAssertionDetails("All artists are present: " + artistsPage.artistsArePresent());
        Assert.assertTrue(artistsPage.artistsArePresent(), "Artists are not displayed");
    }
    @Test(description = "Verify artists is displayed in search results")
    public void searchArtist() {
        String search = "dark";
        artistsPage.searchArtist(search);
        TestListener.logAssertionDetails("Artist shows up from search: " + searchPage.artistExists());
        Assert.assertTrue(searchPage.artistExists());
    }
    @Test(description = "Verify user can play songs from selected artist")
    public void playArtistSongs() {
        artistsPage.clickArtistPlay();
        TestListener.logAssertionDetails("User can play song from artist: " + artistsPage.songIsPlaying());
        Assert.assertTrue(artistsPage.songIsPlaying());
    }
    @Test(description = "Check artists that are displayed in app and compare with artists displayed in db")
    public void checkDbForArtists() throws SQLException, ClassNotFoundException {
        List<String> names = artistsPage.getArtistsNames();
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.checkArtistsInDb();
        while (rs.next()) {
            String name = rs.getString("name");
            dataList.add(name);
        }
        List<String> differences = new ArrayList<>(dataList);
        differences.removeAll(names);
        TestListener.logInfoDetails("Artist names found in app: " + names);
        TestListener.logInfoDetails("Artist names found in db: " + dataList);
        TestListener.logRsDetails("Database contains some artists that are not shown in app: " + !differences.isEmpty());
        TestListener.logAssertionDetails("Artists names are stored correctly: " + (names.size()==dataList.size()));
        Assert.assertNotEquals(names, dataList);
        dataList.clear();
        KoelDbBase.closeDatabaseConnection();
    }
}
