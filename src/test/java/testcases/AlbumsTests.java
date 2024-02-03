package testcases;

import base.BaseTest;
import org.apache.poi.ss.formula.functions.T;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.LoginPage;
import util.listeners.TestListener;

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
    public void setUp() throws MalformedURLException {
        setupBrowser(System.getProperty("baseURL"));
        loginPage = new LoginPage(getDriver());
        albumsPage = new AlbumsPage(getDriver());
        loginPage.loginValidCredentials();
        albumsPage.navigateToAlbumsPage();
    }
    @Test(description = "Verify albums are displayed")
    public void albumsAreDisplayed() {
        TestListener.logAssertionDetails("All albums are present: " + albumsPage.albumsArePresent());
        Assert.assertTrue(albumsPage.albumsArePresent(), "Albums are not displayed");
    }
    @Test(description = "Verify all albums display album titles")
    public void checkAlbumTitles() {
        TestListener.logAssertionDetails("All albums display album titles: " + albumsPage.checkAlbumTitles());
        Assert.assertTrue(albumsPage.checkAlbumTitles(), "One or more albums is not displaying a title");
    }
    @Test(description = "Verify albums have a cover image")
    public void checkAlbumCoverImages() {
        TestListener.logAssertionDetails("All albums have a cover image: " + albumsPage.checkAllAlbumCoverImage());
        Assert.assertTrue(albumsPage.checkAllAlbumCoverImage(), "One or more albums does not have a cover image");
    }
    @Test(description = "Verify albums have album artist")
    public void checkAlbumArtist() {
        TestListener.logAssertionDetails("All albums have an artist: " + albumsPage.checkAlbumArtists());
        Assert.assertTrue(albumsPage.checkAlbumArtists(), "One or more albums does not have an artist");
    }
    @Test(description = "Verify albums have song track total")
    public void checkAlbumSongTrackTotal() {
        TestListener.logAssertionDetails("All albums have total song count: " + albumsPage.albumSongTrackTotal());
        Assert.assertTrue(albumsPage.albumSongTrackTotal(), "One or more albums does not list song track total");
    }
    @Test(description="Verify albums have song duration total")
    public void checkAlbumSongDurtation() {
        TestListener.logAssertionDetails("All albums have song duration total: " + albumsPage.albumDuration());
        Assert.assertTrue(albumsPage.albumDuration(), "One or more albums does not list total song duration");
    }
    @Test(description = "Verify each album has a download button")
    public void checkDownloadButton() {
        TestListener.logAssertionDetails("All albums have a download button: " + albumsPage.checkDownloadButtons());
        Assert.assertTrue(albumsPage.checkDownloadButtons(), "One or more albums is missing a download button");
    }
    @Test(description = "Verify each album has a shuffle button")
    public void checkShuffleButton() {
        TestListener.logAssertionDetails("All albums have a shuffle button: " + albumsPage.checkShuffleButtons());
        Assert.assertTrue(albumsPage.checkShuffleButtons(), "One or more albums is missing a shuffle button");
    }
}
