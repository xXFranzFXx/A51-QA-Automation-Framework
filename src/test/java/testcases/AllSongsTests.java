package testcases;

import base.BaseTest;
import db.KoelDbActions;
import db.KoelDbBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AllSongsPage;
import pages.LoginPage;
import util.TestDataHandler;
import util.listeners.TestListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 *
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
    Map<String, Object> testData = new HashMap<>();

    @BeforeMethod
    @Parameters({"baseURL"})
    public void setUp(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        allSongsPage = new AllSongsPage(getDriver());
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongsPage();
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }

    @Test
    public void checkRows() {
      allSongsPage.findSongRows();
    }
    @Test
    public void getSongTotalFromHeader() {
        Assert.assertEquals(Integer.valueOf(allSongsPage.getSongTotalFromHeader()), 66);
    }
    @Test
    public void songTotalIsDisplayed() {
       Assert.assertTrue(allSongsPage.songTotalIsDisplayed());

    }
    @Test
    public void totalDurationInHeader() {
        Assert.assertTrue(allSongsPage.totalDurationIsDisplayed());
    }
    @Test(description = "get the total amount of songs in the database")
    public void getSongTotalFromDb() throws SQLException, IOException, ClassNotFoundException {
        KoelDbBase.initializeDb();
        KoelDbActions koelDbActions = new KoelDbActions();
        rs = koelDbActions.totalSongCount();
        if(rs.next()) {
            int count = rs.getInt("count");
            testData.put("count", rs.getString("count"));
            Assert.assertEquals(count, 66);
        }
        Assert.assertFalse(false);
    }

}
