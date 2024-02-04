package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AllSongsPage;
import pages.LoginPage;
import util.TestUtil;
import util.listeners.TestListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * INTERNSHIP-82825
 * Story:
 * As a user, I want to be able to open All Songs page to be able to see all existing songs
 * Acceptance Criteria:
 * All Songs page should display the same songs in the app as in DB
 * The total count of songs should be displayed
 * The total duration count of all songs should be displayed
 * ID, Title, Artist, Album, and Time should be correctly displayed
 */
public class AllSongsTests extends BaseTest {
    LoginPage loginPage;
    AllSongsPage allSongsPage;
    ResultSet rs;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        setupBrowser(System.getProperty("baseURL"));
        loginPage = new LoginPage(getDriver());
        allSongsPage = new AllSongsPage(getDriver());
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongsPage();
    }

    @Test(description = "Verify TITLE, ARTIST, ALBUM, and TIME columns are correctly displayed for each song")
    public void checkSongInfo() {
      Assert.assertTrue(allSongsPage.findSongInfo(), "Info is missing in one or more songs, check songs in All Songs page");
    }
    @Test(description = "Count the total number of playable songs and compare that to the total number of songs displayed in All Songs page header")
    public void totalPlayableSongsCount() {
        int manualCount = allSongsPage.getTotalSongsCount();
        int countDisplayedInHeader = Integer.parseInt(allSongsPage.getSongTotalFromHeader());
        TestListener.logInfoDetails("Total songs displayed in All Songs page header: " + countDisplayedInHeader);
        TestListener.logRsDetails("Manual count of songs listed in All Songs page: " + manualCount);
        TestListener.logAssertionDetails("Song total from header matches manual count: " + (countDisplayedInHeader == manualCount));
        Assert.assertNotEquals(manualCount, countDisplayedInHeader, "Double check the assertion, both counts now match");
    }
    @Test(description = "Verify song total is displayed in the All Songs page header")
    public void songTotalIsDisplayed() {
       Assert.assertTrue(allSongsPage.songTotalIsDisplayed(), "Song total not found");
    }
    @Test(description = "Verify total duration of songs is displayed in the All Songs page header")
    public void durationInHeader() {
        Assert.assertTrue(allSongsPage.totalDurationIsDisplayed(), "Total song duration not found");
    }
    @Test(description = "Verify total song count displayed in app matches the total song count from the database")
    public void verifyTotalSongTracks() throws SQLException, IOException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.totalSongCount();
        if(rs.next()) {
            int count = rs.getInt("count");
            int totalSongsInApp = Integer.parseInt(allSongsPage.getSongTotalFromHeader());
            TestListener.logInfoDetails("Total song tracks displayed on All Songs page header: " + totalSongsInApp);
            TestListener.logInfoDetails("Total song tracks in database: " + count);
            TestListener.logAssertionDetails("Total songs in app matches total songs in database: " + (count == totalSongsInApp));
            Assert.assertEquals(count, totalSongsInApp, "Total song count displayed in app does not match the total song count from database");
        }
        KoelDbBase.closeDatabaseConnection();
    }
    @Test(description = "Verify the total duration displayed in All Songs Page matches the sum of all durations in database")
    public void verifyTotalDuration() throws SQLException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.totalDuration();
        if(rs.next()) {
            int durationFromDb = rs.getInt("duration");
            String convertedDurationFromDb = TestUtil.convertSecondToHHMMSSString(durationFromDb);
            String durationInApp = allSongsPage.getDurationFromHeader();
            TestListener.logInfoDetails("Total duration displayed on All Songs page: " + durationInApp);
            TestListener.logInfoDetails("Total duration from database: " + convertedDurationFromDb);
            TestListener.logAssertionDetails("Total duration in app matches total duration in database: " + durationInApp.equals(convertedDurationFromDb));
            Assert.assertEquals(convertedDurationFromDb, durationInApp, "Duration displayed in app does not match total durtion in database");
        }
        KoelDbBase.closeDatabaseConnection();

    }
}
