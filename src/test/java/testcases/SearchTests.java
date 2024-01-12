package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

import java.net.MalformedURLException;
import java.util.List;

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
    @Test(description="Test 'x' button in search field")
        public void cancelButton() {
            String firstSearch = "a";
            String secondSearch = "d";
            homePage = new HomePage(getDriver());
            searchPage = new SearchPage(getDriver());
            homePage.searchSong("a");
            searchPage.clickCancelBtn();
            homePage.searchSong(secondSearch);
            String searchText = searchPage.getSearchResultHeaderText();
            Assert.assertEquals(secondSearch, searchText);
    }
    @Test(description="Use keyboard to clear the search field")
    public void keyboardClear() {
        String firstSearch = "a";
        String secondSearch = "d";
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong("a");
        searchPage.useKeyBoardClear();
        homePage.searchSong(secondSearch);
        String searchText = searchPage.getSearchResultHeaderText();
        Assert.assertEquals(secondSearch, searchText);
    }
    @Test(description = "Verify trailing/heading whitespaces are ignored for search input")
    public void checkWhiteSpace(String str) {
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong(str);
        String searchText = searchPage.getSearchResultHeaderText();
        Assert.assertEquals(str.trim(), searchText);
    }
    @Test(description = "Verify search page can be invoked with keyboard shortcut")
    public void invokeSearchByKeybd() {
        searchPage = new SearchPage(getDriver());
        searchPage.invokeSearchFromKeybd();
        String expectedUrl = "https://qa.koel.app/#!/search";
        Assert.assertEquals(expectedUrl, getDriver().getCurrentUrl());
    }
}
