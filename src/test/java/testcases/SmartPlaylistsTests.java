package testcases;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import util.DataProviderUtil;
import util.RandomString;
import util.TestUtil;

import java.net.MalformedURLException;

/**
 * INTERNSHIP-83417
 * Story:
 * As a user, I want to create a Smart playlist in app, so that I can enjoy the music and modify my settings and preferences
 * Acceptance Criteria:
 * User should be able to create a Smart playlist in app with one rule
 * User should be able to create a Smart playlist in app with multiple rules
 * User should be able to create a Smart playlist in app with Group
 * After creating Smart playlist with a rule, related songs should appear in created Smart playlist
 * If a rule doesn't fit any existing song, empty Smart playlist should be created, 'No songs match the playlist's criteria' should be displayed
 * Playlist name should have the same rules as a regular playlist (any special chars are allowed and 1 to 256 max)
 * All test cases should be automated (for automation engineers)
 */

/**
 * INTERNSHIP-83469
 * Story:
 * As a user, I want to add songs to the Smart playlist in app, so that I can enjoy the music
 * Acceptance Criteria:
 * User should be able to create a Smart playlist in app
 * User should be able to add songs to the Smart playlist by editing existing rules and group settings
 * User’s playlist should be updated correctly in DB
 * User should be able to delete Smart Playlist
 */
public class SmartPlaylistsTests extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        setupBrowser(System.getProperty("baseURL"));
        loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }
    private String generatePlaylistName(int nameLength) {
       return  RandomString.getAlphaNumericString(nameLength);
    }
    @Test(description = "User can create a smart playlist with one rule and verify related songs appear")
    public void createSmartPlaylist() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectOperatorOption("contains")
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Assert.assertFalse(homePage.checkSmartListEmpty(), "No songs match the rule criteria");
    }
    @Test(description = "Verify empty smart playlist is created when no songs match rule criteria")
    public void createSmartPlaylistNoMatches() {
        String playlist = generatePlaylistName(5);
        String expectedText = "No songs match the playlist's criteria.";
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Object[] expected = {true, expectedText};
        Object[] actual = {homePage.checkEmptySmartListIcon(), homePage.emptySmartListMessage()};
        Assert.assertEquals(expected, actual, "There are songs in the smart playlist");
    }
    @Test(description = "Create a smart playlist with group rules")
    public void createSmartPlaylistGroupRules() {
        String playlist = generatePlaylistName(5);
        String[] text = {"dark", "grav"};
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .clickGroupRuleBtn()
                .enterGroupRulesText(text)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist with group rules");
    }
    @Test(description = "Create a smart playlist based on 'Plays' rule criteria")
    public void createSmartPlaylistByPlay() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Plays")
                .enterSmartListIntCriteria(3)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'Plays' model");
    }
    @Test(description= "Create a smart playlist with name has length of one character")
    public void createSmartPlaylistShortName() {
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName("a")
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu("a") , "Unable to create a new smart playlist with a name containing a single character");
    }
    @Test(description = "Create a smart playlist with name that exceeds 256-character length")
    public void createSmartPlaylistLongName() {
        String longName = generatePlaylistName(257);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(longName)
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(longName), "Unable to create a new smart playlist with name exceeding 256 character-length maximum");

    }
    @Test(description = "Create a smart playlist based on 'Date Added' rule criteria")
    public void createSmartPlaylistByDateAdded(){
        String playlist = generatePlaylistName(5);
        String currentDate = TestUtil.getDate();
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Date Added")
                .enterSmartListDateCriteria(currentDate)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'Date Added' model");
    }
    @Test(description = "Verify functionality of 'Cancel' button when creating a new smart playlist")
    public void testCancelButton() {
        String playlist = generatePlaylistName(5);
        String homePageUrl = "https://qa.koel.app/#!/home";
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .clickSmartListCancelBtn();
        Assert.assertEquals(homePageUrl, getDriver().getCurrentUrl(), "Unable to verify functionality of 'Cancel' button");
    }
    @Test(description = "Create a new smart playlist using the operator 'is not' in the rule criteria")
    public void titleIsNot() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectOperatorOption("is not")
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'is not' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'does not contain' in the rule criteria")
    public void titleDoesNotContain() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectOperatorOption("does not contain")
                .enterSmartListTextCriteria("dark")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'does not contain' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'begins with' in the rule criteria")
    public void titleBeginsWith() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectOperatorOption("begins with")
                .enterSmartListTextCriteria("a")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'begins with' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'ends with' in the rule criteria")
    public void titleEndsWith() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectOperatorOption("ends with")
                .enterSmartListTextCriteria("a")
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'ends with' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'is greater than' in the rule criteria")
    public void playsIsGreaterThan() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Plays")
                .selectOperatorOption("is greater than")
                .enterSmartListIntCriteria(3)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'is greater than' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'is less than' in the rule criteria")
    public void playsIsLessThan() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Plays")
                .selectOperatorOption("is less than")
                .enterSmartListIntCriteria(3)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'is less than' operator");
    }
    @Test(description = "Create a new smart playlist using the operator 'is between' in the rule criteria")
    public void playsIsBetween() {
        String playlist = generatePlaylistName(5);
        int[] plays = {2, 5};
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Plays")
                .selectOperatorOption("is between")
                .enterIntCriteria(plays)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'is between' operator");
    }
    @Test(description = "Verify user cannot create a new smart playlist with blank name")
        public void blankPlaylistName() {
            String validationMessage = "Please fill out this field.";
            homePage = new HomePage(getDriver());
            homePage.clickCreateNewPlaylist()
                    .contextMenuNewSmartlist()
                    .clickSaveSmartList();
            Assert.assertEquals(validationMessage, homePage.getNameInputValidationMsg(), "Incorrect or missing form validation message");
    }
    @Test(description = "Verify user cannot create a new smart playlist with no rules criteria")
    public void blankRuleCriteria() {
        String playlist = generatePlaylistName(5);
        String validationMessage = "Please fill out this field.";
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .clickSaveSmartList();
        System.out.println(homePage.getCriteriaInputValidationMsg());
        Assert.assertEquals(validationMessage, homePage.getCriteriaInputValidationMsg(), "Incorrect or missing form validation message");
    }
    @Test(description = "Create a smart playlist based on 'Plays' rule with a negative integer input criteria")
    public void negativeNumberCriteria() {
        String playlist = generatePlaylistName(5);
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Plays")
                .enterSmartListIntCriteria(-3)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist with negative integer rule criteria");
    }
    @Test(description = "Create a smart playlist based on 'in the last' rule criteria")
    public void inTheLastCriteria(){
        String playlist = generatePlaylistName(5);
        String currentDate = TestUtil.getDate();
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Date Added")
                .selectOperatorOption("in the last")
                .enterSmartListIntCriteria(5)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'in the last' operator");
    }
    @Test(description = "Create a smart playlist based on 'not in the last' rule criteria")
    public void notInTheLastCriteria(){
        String playlist = generatePlaylistName(5);
        String currentDate = TestUtil.getDate();
        homePage = new HomePage(getDriver());
        homePage.clickCreateNewPlaylist()
                .contextMenuNewSmartlist()
                .enterSmartListName(playlist)
                .selectModelOption("Date Added")
                .selectOperatorOption("not in the last")
                .enterSmartListIntCriteria(5)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.smartlistAddedToMenu(playlist), "Unable to create a new smart playlist using the 'not in the last' operator");
    }
    @Test(description = "Verify existing smart playlist name can be edited")
    public void editListName() {
        String newName = generatePlaylistName(6);
        homePage = new HomePage(getDriver());
        homePage.cmEditFirstSmartPl()
                .editSmartPlName(newName)
                .clickSaveSmartList();
        Assert.assertTrue(homePage.getFirstSmartPlName().contains(newName), "Unable to edit smart playlist name");
    }
    @Test(description = "Delete all smart playlists")
    public void deleteAllSmartPl() {
        homePage = new HomePage(getDriver());
        homePage.deleteAllPlaylists();
    Assert.assertTrue(homePage.playlistsEmpty());
    }
}

