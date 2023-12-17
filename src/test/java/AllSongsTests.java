import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AlbumsPage;
import pages.AllSongsPage;
import pages.HomePage;
import pages.LoginPage;

import java.net.MalformedURLException;

public class AllSongsTests extends BaseTest {
    LoginPage loginPage;
    AllSongsPage allSongsPage;
    HomePage homePage;
    public AllSongsTests() {
        super();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        allSongsPage = new AllSongsPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(description = "Play the first song on All Songs page")
    public void playFirstSong() {
        loginPage.loginValidCredentials();
        allSongsPage.navigateToAllSongs()
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        Assert.assertTrue(allSongsPage.checkSongPlaying());

    }
    @Test(description = "Click on the Album Tab in the Info Panel")
    public void clickInfoPanelAlbumTab() {
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
