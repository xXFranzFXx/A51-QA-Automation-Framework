import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ArtistsPage;
import pages.LoginPage;

public class ArtistsTests extends BaseTest{


    @Test
    public void playAllSongsByArtist () {
        LoginPage loginPage = new LoginPage(getDriver());
        ArtistsPage artistsPage = new ArtistsPage(getDriver());
        loginPage.loginValidCredentials();
        artistsPage.navigateToArtists()
                .rightClickAlbum()
                .selectPlayAll();
        Assert.assertTrue(artistsPage.soundbarIsDisplayed());
    }
}
