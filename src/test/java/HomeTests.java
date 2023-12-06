import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTests extends BaseTest{
    LoginPage loginPage = new LoginPage(getDriver());
    HomePage homePage = new HomePage(getDriver());
    private final String searchArtist = "Grav";

    @Test
    public void loginValidEmailPasswordTest() {

        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());

        loginPage.provideEmail("fake@fakeaccount.com")
                .providePassword("te$t$tudent")
                .clickSubmitBtn();

        Assert.assertTrue(homePage.getUserAvatar());

    }
//    public void addSongToPlaylist() {
//        loginPage.loginValidCredentials();
//        homePage.searchSong("dark")
//                .clickViewAllButton()
//                .clickFirstSearchResult()
//                .clickGreenAddToBtn()
//                .clickFirstSearchResult();
//        Assert.assertTrue(homePage.notificationMsg());
//
//    }
//    @Test
//    public void hoverOverPlayBtn() throws InterruptedException {
//        loginPage.loginValidCredentials();
//        Assert.assertTrue(homePage.hoverPlay());
//    }
//    @Test
//    //checks visibility/invisibility of info panel by clicking INFO button
//    public void checkInfoPanelVisible () {
//       loginPage.loginValidCredentials();
//       if (homePage.isInfoPanelVisible()){
//           homePage.clickInfoButton();
//           Assert.assertFalse(homePage.isInfoPanelVisible());
//       } else {
//           homePage.clickInfoButton();
//           Assert.assertTrue(homePage.isInfoPanelVisible());
//       }
//    }
//    @Test
//    public void checkLyricsInfoPanelTab() {
//        loginPage.loginValidCredentials();
//
//        homePage.searchSong(searchArtist)
//                .doubleClickFirstSearchResult()
//                .clickInfoButton()
//                .clickLyricsTab();
//        String lyricsInfoText = "No lyrics available. Are you listening to Bach?";
//        Assert.assertEquals(homePage.getLyricsText().toString(), lyricsInfoText);
//    }
//    @Test
//    public void checkArtistInfoPanelTab() {
//        loginPage.loginValidCredentials();
////        String searchArtist = "Grav";
//        homePage.searchSong(searchArtist)
//                .doubleClickFirstSearchResult()
//                .clickInfoButton()
//                .clickArtistTab();
//        Assert.assertEquals(homePage.getArtistTabText().toString(), searchArtist);
//    }
//    @Test
//    public void checkAlbumInfoPanelTab() {
//        loginPage.loginValidCredentials();
//        String searchResultSong = homePage.getSearchResultSongText().toString();
//        String albumInfoPanelText = homePage.getAlbumTabText().toString();
//        homePage.searchSong(searchArtist)
//                .doubleClickFirstSearchResult()
//                .clickInfoButton()
//                .clickAlbumTab();
//        Assert.assertTrue(albumInfoPanelText.contains(searchResultSong));
//    }
}
