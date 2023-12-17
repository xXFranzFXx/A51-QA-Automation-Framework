import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.ArtistsPage;
import pages.LoginPage;

import java.net.MalformedURLException;

public class ArtistsTests extends BaseTest {
    LoginPage loginPage;
    ArtistsPage artistsPage;
    public ArtistsTests() {
        super();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        artistsPage = new ArtistsPage(getDriver());
    }

    @Test(description = "Play all songs by an artist")
    public void playAllSongsByArtist () {
        loginPage.loginValidCredentials();
        artistsPage.navigateToArtists()
                .rightClickAlbum()
                .selectPlayAll();
        Assert.assertTrue(artistsPage.soundbarIsDisplayed());
        Reporter.log("Added all songs from selected artist album to current queue");
    }
}
