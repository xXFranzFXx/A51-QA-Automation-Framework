import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AllSongsPage;
import pages.HomePage;
import pages.LoginPage;

public class AllSongsTests extends BaseTest {


    @Test(description = "Play the first song on All Songs page")
    public void playFirstSong() {
        LoginPage loginPage = new LoginPage(getDriver());
        AllSongsPage allSongsPage = new AllSongsPage(getDriver());
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongs()
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        Assert.assertTrue(allSongsPage.checkSongPlaying());

    }
    @Test(description = "Click on the Album Tab in the Info Panel")
    public void clickInfoPanelAlbumTab() {
        LoginPage loginPage = new LoginPage(getDriver());
        AllSongsPage allSongsPage = new AllSongsPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongs()
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        homePage.clickAlbumTab();
        Assert.assertTrue(homePage.checkAlbumTabText());

    }
}
