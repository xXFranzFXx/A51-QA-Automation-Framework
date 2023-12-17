import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.HomePage;
import pages.LoginPage;

import java.net.MalformedURLException;

public class AlbumsTests extends BaseTest {
    LoginPage loginPage;
    AlbumsPage albumsPage;
    public AlbumsTests() {
        super();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        albumsPage = new AlbumsPage(getDriver());
    }

    @Test(description = "Right click on an album and play all songs")
    public void playFirstAlbumSongs() {
        loginPage.loginValidCredentials();
        albumsPage.navigateToAlbums()
                .rightClickAlbum()
                .selectPlayAll();
        Assert.assertTrue(albumsPage.checkAlbumSongPlaying());
        Reporter.log("Added all songs from selected album to current queue", true);
    }
}
