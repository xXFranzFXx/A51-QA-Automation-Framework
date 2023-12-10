import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.LoginPage;

public class AlbumsTests extends BaseTest {

    @Test(description = "Right click on an album and play all songs")
    public void playFirstAlbumSongs() {
        LoginPage loginPage = new LoginPage(getDriver());
        AlbumsPage albumsPage = new AlbumsPage(getDriver());
        loginPage.loginValidCredentials();
        albumsPage.navigateToAlbums()
                .rightClickAlbum()
                .selectPlayAll();
        Assert.assertTrue(albumsPage.checkAlbumSongPlaying());
        Reporter.log("Added all songs from selected album to current queue", true);
    }
}
