package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
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
    @BeforeClass
    public void setEnv(){
        loadEnv();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        allSongsPage = loginPage.loginValidCredentials().clickAllSongs();
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }

    @Test(description = "Play the first song on All Songs page")
    public void playFirstSong() {
       allSongsPage
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        Assert.assertTrue(allSongsPage.checkSongPlaying());

    }
    @Test(description = "Click on the Album Tab in the Info Panel")
    public void clickInfoPanelAlbumTab() {
        allSongsPage
                .checkHeaderTitle()
                .contextClickFirstSong()
                .choosePlayOption();
        homePage.clickAlbumTab();
        Assert.assertTrue(homePage.checkAlbumTabText());
    }
    @Test(description="Unlike all liked songs")
    public void unlikeAll () {
            allSongsPage.unlikeSongs();
            Assert.assertTrue(allSongsPage.checkUnliked());
    }
}
