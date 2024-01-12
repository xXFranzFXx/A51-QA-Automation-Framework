package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import util.listeners.TestListener;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Story:
 * As a user, I want to perform a search to find an existing song
 * Acceptance Criteria:
 * After searching for existing songs, results if matched should be populated on the Search results page.
 * Results should be displayed on each section: Songs, Artist, Album. Also, data should be displayed in each section, no matter what we are looking for: a song, artist or album.
 * Show empty list if searched song\artist\album doesn't exist in the Koel app with the message "no results".
 * Ignore trailing\heading white spaces (Examples: "  chill song " should be searched as "chill song" or "  chill song", "chill song ").
 * BUG >>Search should be case sensitive
 * User can clear the search query with keyboard and 'x' button. Search results should be cleared on the search field and the search page in each section: song, artist or album.
 */

public class SearchTests extends BaseTest {
    HomePage homePage;
    LoginPage loginPage;
    SearchPage searchPage;


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
    @Parameters({"searchString"})
    public void checkSearchResults(String searchString) {
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong(searchString);
        TestListener.logInfoDetails("Search input: " + searchString);
        TestListener.logAssertionDetails("Results for songs are empty: " + searchPage.isSearchResultEmpty("song"));
        TestListener.logAssertionDetails("Results for artist are empty: " + searchPage.isSearchResultEmpty("artist"));
        TestListener.logAssertionDetails("Results for album are empty: " + searchPage.isSearchResultEmpty("album"));
        Assert.assertFalse(searchPage.isSearchResultEmpty("song"));
        Assert.assertFalse(searchPage.isSearchResultEmpty("artist"));
        Assert.assertFalse(searchPage.isSearchResultEmpty("album"));
    }
    @Test(description = "Verify no info is displayed when search returns no matches")
    @Parameters({"searchString2"})
    public void checkEmptyResults(String searchString2) {
        homePage = new HomePage(getDriver());
        searchPage = new SearchPage(getDriver());
        homePage.searchSong(searchString2);
        TestListener.logInfoDetails("Search input: " + searchString2);

        TestListener.logAssertionDetails("Results for songs are empty: " + searchPage.noneFoundTextExists("song"));
        TestListener.logAssertionDetails("Results for artist are empty: " + searchPage.noneFoundTextExists("artist"));
        TestListener.logAssertionDetails("Results for album are empty: " + searchPage.noneFoundTextExists("album"));

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
