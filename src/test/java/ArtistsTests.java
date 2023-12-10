import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.ArtistsPage;
import pages.LoginPage;

public class ArtistsTests extends BaseTest {
    LoginPage loginPage = new LoginPage(getDriver());
    ArtistsPage artistsPage = new ArtistsPage(getDriver());

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
