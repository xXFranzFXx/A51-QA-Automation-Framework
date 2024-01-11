package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

import java.net.MalformedURLException;

public class SearchTests extends BaseTest {
    HomePage homePage;
    LoginPage loginPage;
    SearchPage searchPage;

    @BeforeClass
    public void setEnv() {
        loadEnv();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }
    @AfterMethod
    public void close() {
        closeBrowser();
    }

    @Test(description = "Verify info is displayed in Songs, Artists, Albums section when searching")
    public void checkSearchResults() {
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong("dar");

     Assert.assertFalse(searchPage.isSearchResultEmpty("song"));
     Assert.assertFalse(searchPage.isSearchResultEmpty("artist"));
     Assert.assertFalse(searchPage.isSearchResultEmpty("album"));
    }
    @Test(description = "Verify no info is displayed when search returns no matches")
    public void checkEmptyResults() {
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong("a");

        Assert.assertTrue(searchPage.noneFoundTextExists("song"));
        Assert.assertTrue(searchPage.noneFoundTextExists("artist"));
        Assert.assertTrue(searchPage.noneFoundTextExists("album"));
    }
    @Test(description="Test 'x' button in search filed")
        public void cancelButton() {
            homePage = new HomePage(getDriver());
            searchPage = new SearchPage(getDriver());
            homePage.searchSong("a");
            searchPage.getShadowCancelBtn().click();
            System.out.println(searchPage.getSearchPlaceholderTxt().getText());
    }
}
