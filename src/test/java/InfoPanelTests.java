import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;


/**
 * Story:
 * As a user, I want to be able to observe Lyrics, Artist and Album information in the Info panel
 * Acceptance Criteria:
 * 1 Album name and cover should be displayed for a playing song
 * 2 Lyrics should be displayed for a playing song
 * 3 Artist name should be displayed for a playing song
 * 4 Info panel should appear after clicking on 'INFO' button on the music player
 * 5 Info panel should hide after clicking on 'INFO'  button on the music player
 * 6 User should be able to shuffle songs on 'INFO' panel
 */
public class InfoPanelTests extends BaseTest {
    private final String searchArtist = "Grav";

    @Test
    //checks visibility/invisibility of info panel by clicking INFO button (Acceptance criteria 5)
    public void checkInfoPanelDisappears() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.clickInfoBtnActive();
        Assert.assertTrue(homePage.isInfoPanelTabsInvisible());

    }

    //(Acceptance criteria 4)
    @Test
    public void checkInfoPanelIsVisible() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.isInfoPanelVisible());

    }

    @Test
    //click each tab and verify correct info is displayed in info panel then press the shuffle button (Acceptance critera 1,2,3)
    public void checkInfoPanelTabs() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        String lyricsInfoText = "No lyrics available. Are you listening to Bach?";
        String albumInfoText = "Play all songs in the album Dark Days EP";

        homePage.searchSong(searchArtist)
                .clickSearchResultThumbnail()
                .clickInfoButton();
        String displayedLyricsString;
        displayedLyricsString = homePage.clickLyricsTab();
        Assert.assertEquals(displayedLyricsString, lyricsInfoText);

        String displayedArtistString;
        displayedArtistString = homePage.clickArtistTab();
        Assert.assertEquals(displayedArtistString, searchArtist);

        String displayedAlbumString;
        displayedAlbumString = homePage.clickAlbumTab();
        Assert.assertEquals(displayedAlbumString, albumInfoText);

    }

    //click shuffle button in info panel albums tab (Acceptance criteria 6)
    @Test
    public void checkShufflePlayBtn() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.searchSong(searchArtist)
                .clickSearchResultThumbnail()
                .clickAlbumTab();
        homePage.clickAlbumTabShuffleBtn();
        Assert.assertTrue(homePage.checkQueueTitle());

    }
}