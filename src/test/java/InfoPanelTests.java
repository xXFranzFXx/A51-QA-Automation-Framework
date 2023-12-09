import org.testng.Assert;
import org.testng.Reporter;
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

    @Test(description = "checks for visibility of the info panel upon logging in")
    public void toggleInfoPanel() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.checkVisibility());
        Reporter.log("Info panel is visible");

    }
    @Test(description = "Login, search for an artist and play song, then test shuffle play button in the Album Tab", dependsOnMethods = { "toggleInfoPanel" })
    public void checkShufflePlayBtn() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        homePage.searchSong(searchArtist)
                .clickSearchResultThumbnail()
                .clickAlbumTab();
        homePage.clickAlbumTabShuffleBtn();
        Assert.assertTrue(homePage.checkQueueTitle());
        Reporter.log("Successfully shuffled songs using Album tab shuffle button", true);

    }
    @Test(description = "click each tab and verify correct info is displayed in info panel then press the shuffle button", dependsOnMethods = { "toggleInfoPanel", "checkShufflePlayBtn" })
    public void checkInfoPanelTabs() {
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.loginValidCredentials();
        String lyricsInfoText = "No lyrics available. Are you listening to Bach?";
        String albumInfoText = "Play all songs in the album Dark Days EP";
        try {
            homePage.searchSong(searchArtist)
                    .clickSearchResultThumbnail();
            String displayedLyricsString;
            displayedLyricsString = homePage.clickLyricsTab();
            Assert.assertEquals(displayedLyricsString, lyricsInfoText);
            Reporter.log("Verified functionality of Lyrics tab in info panel", true);

        }catch(Exception e) {
            Reporter.log("Failed to verify Lyrics tab" + e);
        }
        try {
            String displayedArtistString;
            displayedArtistString = homePage.clickArtistTab();
            Assert.assertEquals(displayedArtistString, searchArtist);
            Reporter.log("Verified functionality of Artist tab in info panel", true);

        }catch(Exception e) {
            Reporter.log("Failed to verify Artist tab" + e);
        }
        try {
        String displayedAlbumString;
        displayedAlbumString = homePage.clickAlbumTab();
        Assert.assertEquals(displayedAlbumString, albumInfoText);
            Reporter.log("Verified functionality of Album tab in info panel", true);

        }catch(Exception e) {
            Reporter.log("Failed to verify Album tab" + e);
        }
        Reporter.log("Verified functionality of all tabs in info panel", true);
    }
}