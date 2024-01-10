package testcases;

import base.BaseTest;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AllSongsPage;
import pages.FavoritesPage;
import pages.HomePage;
import pages.LoginPage;
import util.listeners.TestListener;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

public class FavoritesTests extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    AllSongsPage allSongsPage;
    FavoritesPage favoritesPage;

    private boolean checkDownloadedFiles() {
        File rootFolder = new File(System.getProperty("java.io.tmpdir"));
        File[] downloadedFiles = rootFolder.listFiles(file ->
                file.getName().contains(".mp3") || file.getName().contains(".crdownload")
        );
        return downloadedFiles != null;
    }
    @BeforeClass
    public void setEnv() {
        loadEnv();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setUp(String baseURL) throws MalformedURLException {
      setupBrowser(baseURL);
      loginPage = new LoginPage(getDriver());
      loginPage.loginValidCredentials();
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }
    @Test(description="Add songs to favorites playlist and verify they are displayed in the favorites playlist page", priority=0)
    public void likeSongs() {
        favoritesPage = new FavoritesPage(getDriver());
        allSongsPage = new AllSongsPage(getDriver());
        allSongsPage.allSongsPage();
        allSongsPage.likeSongs();
        allSongsPage.favorites();
        Assert.assertFalse(favoritesPage.isFavoritesEmpty());
    }
    @Test(description = "Remove all songs from favorites playlist and verify no songs are displayed", priority=2)
    public void unlikeAllSongs(){
        favoritesPage = new FavoritesPage(getDriver());
        favoritesPage.favorites();
        favoritesPage.unlikeAllSongs();
        Assert.assertTrue(favoritesPage.checkPlaylistEmptyIcon());
    }
    @Test(description = "Download a song from the favorites playlist page and verify that the song is downloading", priority=1)
    public void downloadSong() {
        favoritesPage = new FavoritesPage(getDriver());
        favoritesPage.favorites();
        favoritesPage.contextClickFirstSong()
                .selectDownloadFromCM();
        Assert.assertTrue(checkDownloadedFiles());
    }

}
