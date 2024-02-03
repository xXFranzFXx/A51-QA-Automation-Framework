package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.LoginPage;

import java.net.MalformedURLException;

/**
 * Story:
 * As a user, I want to be able to open Albums page to be able to see all existing albums
 * Acceptance Criteria:
 * Albums cover (if exist) should be present
 * If Album cover is not existing standard Koel album cover should be present
 * Album name should be displayed
 * Artist name should be displayed
 * Songs count should be displayed and reflect the actual number of songs in the album
 * Shuffle icon should be present
 * Download icon should be present
 */
public class AlbumsTests extends BaseTest {
    LoginPage loginPage;
    AlbumsPage albumsPage;
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

    @Test(description = "Verify albums are displayed")
    public void albumsAreDisplayed() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().albumsArePresent());
    }
    @Test(description = "Verify all albums display album titles")
    public void checkAlbumTitles() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().checkAlbumTitles());
    }
    @Test(description = "Verify albums have a cover image")
    public void checkAlbumCoverImages() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().checkAllAlbumCoverImage());
    }
    @Test(description = "Verify albums have album artist")
    public void checkAlbumArtist() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().checkAlbumArtists());
    }
    @Test(description = "Verify albums have song track total")
    public void checkAlbumSongTrackTotal() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().albumSongTrackTotal());
    }
    @Test(description="Verify albums have song duration total")
    public void checkAlbumSongDurtation() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().albumDuration());
    }
    @Test(description = "Verify each album has a download button")
    public void checkDownloadButton() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().checkDownloadButtons());
    }
    @Test(description = "Verify each album has a shuffle button")
    public void checkShuffleButton() {
        albumsPage = new AlbumsPage(getDriver());
        Assert.assertTrue(albumsPage.navigateToAlbumsPage().checkShuffleButtons());
    }
}
