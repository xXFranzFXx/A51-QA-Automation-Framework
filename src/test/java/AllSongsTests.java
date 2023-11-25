import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AllSongsPage;
import pages.HomePage;
import pages.LoginPage;

public class AllSongsTests extends BaseTest{
    LoginPage loginPage = new LoginPage(getDriver());
    AllSongsPage allSongsPage = new AllSongsPage(getDriver());

    @Test
    public void playFirstSong() {
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongs()
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        Assert.assertTrue(allSongsPage.checkSongPlaying());
    }
}
