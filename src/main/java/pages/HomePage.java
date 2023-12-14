package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;


public class HomePage extends BasePage {

    //user avatar icon element
    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;
    @FindBy(xpath = "//*[@id=\"userBadge\"]/a[1]/span")
    private By userBadgeLocator;
    //first created playlist
    @FindBy(css = ".playlist:nth-child(3)")
    private By firstPlaylist;

    //playlist name input field, accessed when creating a new playlist
    @FindBy(css = "input[name='name']")
    private WebElement playlistInputField;

    //"plus" icon for creating new playlist
    @FindBy(css = ".fa-plus-circle")
    private WebElement addNewPlaylistIcon;
    //delete playlist button
    @FindBy(className = "btn-delete-playlist")
    private By deletePlaylistBtn;
    //notification message
    @FindBy(css = ".all-songs tr.song-item:nth-child(1)")
    private WebElement firstSongLocator;
    @FindBy(css = ".show-success")
    private WebElement successNotification;
    //block containing all songs
    @FindBy(css = ".songs")
    private WebElement allSongs;
    @FindBy(css = "[data-testid='playlist-context-menu-create-simple']")
    private WebElement selectCreateNewPlaylist;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/input")
    private WebElement searchSongInput;

    //play button used for hoverplay method
    @FindBy(css = "[data-testid='play-btn']")
    private WebElement play;


    @FindBy(css = "h1 button[data-testid='view-all-songs-btn']")
    private By viewAllBtnLocator;

    @FindBy(xpath = "//section[@class=\"recent\"]//h1")
    private WebElement recentlyPlayedViewAllBtn;
    private By rPPlayedViewAllBtn = By.xpath("//section[@class=\"recent\"]//h1/button");
    //AddTo dropdown menu choice in the context menu when right-clicking a song
    @FindBy(xpath = "//*[@id=\"app\"]/nav/ul/li[4]")
    private WebElement addToLocator;

    //first playlist on "AddTo" dropdown menu
    @FindBy(xpath = "//*[@id=\"app\"]/nav/ul/li[4]/ul/li[7]")
    private WebElement playlistLocator;

    @FindBy(xpath = "//*[@id=\"songResultsWrapper\"]/header/div[3]/span/button[2]")
    private By greenAddToBtn;

    //first song returned from search
    @FindBy(xpath = "//*[@id=\"songResultsWrapper\"]/div/div/div[1]/table/tr[1]")
    private By firstSongResult;

    /**
     * Search results components start
     */
    @FindBy(xpath = "//*[@id=\"searchExcerptsWrapper\"]/div/div/section[1]/ul/article/span[2]/span[1]/text()")
    private WebElement searchResultSongText;
    @FindBy(xpath = "//*[@id=\"searchExcerptsWrapper\"]/div/div/section[1]/ul/article/span[2]/span[1]")
    private WebElement searchResultSongLocator;

    @FindBy(xpath = "//*[@id=\"searchExcerptsWrapper\"]/div/div/section[1]/ul/article/span[2]/span[1]/span/a")
    private WebElement searchResultArtistText;
    /**
     * Search results components end
     */

    /**
     * INFO panel components start
     */
    @FindBy(css = "[data-testid='extra-panel']")
    private WebElement infoPanel;

    @FindBy(css = "#extra.text-secondary.showing")
    private WebElement infoPanelShowing;

    @FindBy(css = "button[title='View song information']")
    private WebElement infoButton;
    @FindBy(xpath = "//*[@id=\"mainFooter\"]/div[2]/div[2]/div/button[1]")
    private WebElement infoButtonActive;
    @FindBy(xpath = "//button[contains(text(), 'Lyrics')]")
    private WebElement lyricsTab;
    @FindBy(xpath = "//*[@id='extraTabArtist']")
    private WebElement artistTab;
    @FindBy(xpath = "//*[@id=extraTabAlbum']")
    private WebElement albumTab;
    @FindBy(xpath = "//*[@id='extraPanelAlbum']/article/main/span/a")
    private WebElement albumTabCoverFinder;
    @FindBy(xpath = "//*[@id=\"extraPanelAlbum\"]/article/main/span/a/text()")
    private WebElement albumTabCoverPlayBtnText;
    @FindBy(xpath = "//*[@id='extraPanelArtist']/article/h1/span/text()")
    private WebElement artistTabInfoText;
    @FindBy(xpath = "//*[@id=\"extraPanelArtist\"]/article/h1/button")
    private WebElement artistTabShuffleBtn;
    @FindBy(xpath = "//*[@id=\"lyrics\"]/div/p/span/text()")
    private WebElement lyricsTabInfoText;
    @FindBy(xpath = "//h1[text()[normalize-space()=' Current Queue ']]")
    private WebElement currentQueueText;
    @FindBy(css = "section#extra .tabs")
    private WebElement infoPanelTabsGroupLocator;
    @FindBy(xpath = "//*[@id=\"homeWrapper\"]/header/div[2]/h1")
    private WebElement welcomeMsg;
    @FindBy(css = "section.recent .text-secondary")
    private WebElement emptyListPlaceHolderText;
    @FindBy(xpath = "//span[@class='right']/a[@class='shuffle-album']")
    private WebElement rAShuffleBtn;
    @FindBy(xpath = "//span[@class='right']/a[@class='download-album']")
    private WebElement rADownloadBtn;
    @FindBy(xpath = "//span[@class='album-thumb-wrapper']//span[@class='album-thumb']")
    private WebElement playBtnBefore;
    @FindBy(xpath = "//i[@class='fa-play']")
    private WebElement hoverPlayBtnLocator;
    @FindBy(css = "#playlists h1")
    private WebElement createNewPlaylistBtnLocator;
    @FindBy(xpath = "//*[@id='playlists']//i[@data-testid='sidebar-create-playlist-btn']")
    private WebElement createNewPlaylistBtn;
    @FindBy(xpath = "//*[@data-testid=\"playlist-context-menu-create-simple\"]")
    private WebElement contextMenuNewPlaylst;
    @FindBy(xpath = "//*[@data-testid=\"playlist-context-menu-create-smart\"]")
    private WebElement contextMenuNewSmartlst;
    @FindBy(css = "input[name='name']")
    private WebElement newPlaylistInput;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']")
    private WebElement smartListModal;
    @FindBy(xpath = "//span[@class=\"value-wrapper\"]/input[@type=\"text\"]")
    private WebElement smartListCriteriaInput;
    @FindBy(xpath = "//*[@id=\"mainWrapper\"]/div/div/div/form/div/div[1]/input")
    private WebElement smartListFormNameInput;
    @FindBy(xpath = "//button[text()=\"Save\"]")
    private WebElement smartListSaveButton;
    private final By songTitle = By.cssSelector("section#playlistWrapper td.title");
    private final By searchResultThumbnail = By.cssSelector("section[data-testid=\"song-excerpts\"] span.cover:nth-child(1)");
    private final By lyricsTabLocator = By.id("extraTabLyrics");
    private final By lyricsTabInfo = By.cssSelector(".none span");
    private final By artisTabLocator = By.id("extraTabArtist");
    private final By artistTabInfo = By.cssSelector("[data-test='artist-info'] h1.name span");
    private final By albumTabLocator = By.id("extraTabAlbum");
    private final By albumTabInfo = By.cssSelector("main span a.control.control-play");
    private final By albumTabShuffleBtn = By.cssSelector("article[data-test=\"album-info\"] .fa-random");
    private final By currentQueueHeader = By.cssSelector("#queueWrapper .heading-wrapper h1");
    private final By currentQueueLocator = By.xpath("//h1[text()[normalize-space()=' Current Queue ']]");
    private final By logoutButtonLocator = By.cssSelector("i.fa.fa-sign-out");
    private final By badgeNameTextLocator = By.xpath("//*[@id=\"userBadge\"]/a[1]/span");
    private final By playPauseBtn = By.xpath("//span[@title='Play or resume']//i[@class='fa fa-play']");
    private final By recentlyAddedlistItems = By.xpath("//ol[@class=\"recently-added-album-list\"][1]/li");
    private final By searchResultsGroup = By.cssSelector("#searchExcerptsWrapper .results section");
    private final By selectNewSmartList = By.cssSelector("li[data-testid=\"playlist-context-menu-create-smart\"]");

    @FindBy(xpath = "//article/footer/div/span[@class=\"sep text-secondary\"]")
    private WebElement rAThumbnailTitle;

    /**
     * INFO panel components end
     */
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    private final String strUrl = driver.getCurrentUrl();

    public boolean getUserAvatar() {
        return userAvatarIcon.isEnabled();
    }

    public HomePage chooseFirstPlaylist() {
        click(firstPlaylist);
        return this;
    }

    public HomePage deletePlaylist() {
        click(deletePlaylistBtn);
        return this;
    }

    public HomePage searchSong(String song) {

        searchSongInput.clear();
        searchSongInput.sendKeys(song);
        return this;
    }

    public boolean notificationMsg() {
        findElement(successNotification);
        return successNotification.isDisplayed();
    }

    public boolean hoverPlay() throws InterruptedException {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(playPauseBtn));
        actions.moveToElement(btn).perform();
        return playBtnBefore.isEnabled();
    }

    public int countSongs() {
        return findElements(songTitle).size();
    }

    public HomePage choosePlaylistByName(String playlistName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + playlistName + "')]"))).click();
        return this;
    }

    public HomePage doubleClickFirstPlaylist() {
        doubleClick((WebElement) firstPlaylist);
        return this;
    }

    public HomePage enterNewPlaylistName(String newPlaylistName) {
        findElement(playlistInputField);
        playlistInputField.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        playlistInputField.sendKeys(newPlaylistName);
        playlistInputField.sendKeys(Keys.ENTER);
        return this;
    }

    public HomePage contextClickFirstSong() {
        contextClick(firstSongLocator);
        return this;
    }

    public HomePage clickViewAllButton() {
        click(viewAllBtnLocator);
        return this;
    }
    public HomePage clickRPViewAllBtn() {
        actions.moveToElement(recentlyPlayedViewAllBtn).perform();
        click(rPPlayedViewAllBtn);

        return this;
    }


    public HomePage clickFirstSearchResult() {
        click(firstSongResult);
        return this;
    }

    public HomePage clickGreenAddToBtn() {
        click(greenAddToBtn);
        return this;
    }

    public void clickInfoButton() {
        infoButton.click();
    }
    //click the info button and check for info panel visibility, click again if invisible this verifies it disappears when clicked
    //if the first click turns on visibility, click again to make it invisible, and negative assert visibility of info panel this verifies that the info button toggles the visibility.

    public boolean checkVisibility() {
        clickInfoBtnActive();
        if (!lyricsTab.isDisplayed()) {
            clickInfoButton();
            Reporter.log("Info Panel is now visible.", true);
            return lyricsTab.isDisplayed();
        } else {
            clickInfoButton();
            Reporter.log("Info Panel is now hidden.", true);
            return !lyricsTab.isDisplayed();
        }
    }

    public String clickLyricsTab() {
        click(lyricsTabLocator);
        WebElement lyricsInfoText = wait.until(ExpectedConditions.presenceOfElementLocated(lyricsTabInfo));
        return lyricsInfoText.getText();
    }

    public String clickArtistTab() {
        click(artisTabLocator);
        WebElement artistInfoText = wait.until(ExpectedConditions.presenceOfElementLocated(artistTabInfo));
        return artistInfoText.getText();
    }

    public String clickAlbumTab() {
        if (isInfoPanelVisible().isDisplayed()) {
            click(albumTabLocator);
        } else {
            clickInfoButton();
        }
//        click(albumTabLocator);
        WebElement albumInfoText = wait.until(ExpectedConditions.presenceOfElementLocated(albumTabInfo));
        return albumInfoText.getText();
    }

    public HomePage checkHeaderTitle() {
        WebElement headerTitle = wait.until(ExpectedConditions.presenceOfElementLocated(currentQueueHeader));
        Assert.assertTrue(headerTitle.isDisplayed());
        return this;
    }

    public void clickAlbumTabShuffleBtn() {
        click(albumTabShuffleBtn);

    }

    public Boolean checkQueueTitle() {
        String url = "https://qa.koel.app/#!/queue";
        String currentUrl = driver.getCurrentUrl();
        return url.equals(currentUrl);
    }

    public HomePage doubleClickFirstSearchResult() {
        actions.doubleClick(searchResultSongText).perform();
        return this;
    }

    public WebElement isInfoPanelVisible() {
        return findElement(infoPanelShowing);
    }

    public boolean isInfoPanelTabsInvisible() {
        return wait.until(ExpectedConditions.invisibilityOf(infoPanelTabsGroupLocator));
    }

    public HomePage getSearchResultSongText() {
        searchResultSongLocator.getText();
        return this;
    }

    public HomePage getArtistTabText() {
        artistTabInfoText.getText();
        return this;
    }

    //   public HomePage getAlbumTabText() {
//
//        albumTabCoverPlayBtnText.getText();
//        return this;
//   }
    public boolean checkAlbumTabText() {
        findElement(albumTabCoverFinder);
//       Assert.assertTrue(albumTabCoverFinder.isDisplayed());
        return albumTabCoverFinder.isDisplayed();
    }

    public void clickInfoBtnActive() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-testid='toggle-extra-panel-btn]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".control.text-uppercase.active"))).click();
    }

    public HomePage clickSearchResultThumbnail() {
        WebElement thumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultThumbnail));
        thumbnail.click();
        return this;
    }

    public boolean checkForLogoutBtn() {
        WebElement logoutButton = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButtonLocator));
        return logoutButton.isDisplayed();
    }

    public void clickLogoutButton() {
        click(logoutButtonLocator);
    }

    public String getWelcomMsg() {
        return findElement(welcomeMsg).getText();
    }

    public boolean noRecentlyPlayed() {
        return findElement(emptyListPlaceHolderText).isDisplayed();

    }

    public void clickFooterPlayBtn() {
        actions.moveToElement(play).perform();
        click(playPauseBtn);
    }

    public boolean getUserBadgeText(String name) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(badgeNameTextLocator, name));
    }

    public int searchResultsSize() {
        return findElements(searchResultsGroup).size();
    }

    public boolean searchResultsExists() {
        return (searchResultsSize() > 0);
    }

    public int recentlyPlayedListSize() {
        return findElements(By.className("recent-song-list")).size();
    }

    public boolean recentlyPlayedListExists() {
        int li = findElements(By.className("recent-song-list")).size();
        return (li > 0);
    }
    @FindBy(css = "section.recent p.text-secondary")
    private WebElement rPEmptyText;
    public boolean noRecentlyPlayedList() {
        String text = "Your recently played songs will be displayed here.";
        wait.until(ExpectedConditions.textToBePresentInElement(rPEmptyText, text));
        return findElement(rPEmptyText).isDisplayed();
    }

    public boolean recentlyAddedListHasAlbumTitles() {
        List<WebElement> li = findElements(recentlyAddedlistItems);
        Reporter.log("Recently Added list items" + li, true);
        for (WebElement l : li) {
            Reporter.log("list item" + l, true);
            WebElement albumTitle = findElement(rAThumbnailTitle);
            Reporter.log("Checking for album titles" + albumTitle.getText(), true);
            return albumTitle.getText().equals("by");
        }
        return false;
    }

    public boolean checkRAListButtonsOnHover() {
        List<WebElement> li = findElements(recentlyAddedlistItems);
        Reporter.log("Recently Added list items" + li, true);
        for (WebElement l : li) {
            Reporter.log("list item" + l, true);
            actions.moveToElement(l).perform();
            Reporter.log("Checking for buttons", true);
            if (rAShuffleBtn.isDisplayed()) {
                return rADownloadBtn.isDisplayed();
            }
            Reporter.log("both buttons present", true);
        }
        Reporter.log("Couldn't locate buttons", true);
        return false;
    }



    public String getSearchedTitleFromResults() {
        return getSearchedSongTitle();
    }

    public HomePage clickAboutLink() {
        about();
        return this;
    }

    public boolean aboutModalVisible() {
        return checkAboutModal();
    }

    public void closeModalPopup() {
        closeModal();
    }

    public boolean isModalClosed() {
        return modalIsClosed();
    }

    /**
     * Side menu links actions
     */
    public void clickHome() {
        homePage();
    }

    public HomePage clickCurrentQueue() {
        currentQueuePage();
        return this;
    }

    public void clickAllSongs() {
        allSongsPage();
    }

    public void clickAlbums() {
        albumsPage();
    }

    public void clickArtists() {
        artistsPage();
    }

    public void clickRecentlyPlayed() {
        recentlyPlayedPage();
    }

    public void clickFavorites() {
        favorites();
    }

    public void clickCreateNewPlaylist() {
        actions.moveToElement(createNewPlaylistBtnLocator).perform();
        createNewPlaylistBtn.click();
    }

    public void contextMenuNewPlaylist() {
        actions.moveToElement(contextMenuNewPlaylst).perform();
        clickElement(contextMenuNewPlaylst);
    }

    public void contextMenuNewSmartlist() {
       actions.moveToElement(contextMenuNewSmartlst).perform();
       click(selectNewSmartList);
    }

    public void enterPlaylistName(String playlist) {
        WebElement input = findElement(newPlaylistInput);
        input.sendKeys(playlist);
        input.sendKeys(Keys.ENTER);
    }

    public boolean playlistAddedToMenu(String playlist) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"playlist playlist\"]/a[text()='" + playlist + "']"))).isDisplayed();
    }

    public boolean smartListModalVisible() {
        return findElement(smartListModal).isDisplayed();
    }

    public void enterSmartListName(String smartList) {
        WebElement input = findElement(smartListFormNameInput);
        input.sendKeys("newSmartList");

    }
    public void enterSmartListCriteria(String criteria) {
        WebElement input = findElement(smartListCriteriaInput);
        input.sendKeys(criteria);


    }
    public boolean smartlistAddedToMenu(String playlist) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"playlist playlist smart\"]/a[text()='" + playlist + "']"))).isDisplayed();
    }

    public void clickSaveSmartList() {
        findElement(smartListSaveButton).click();
    }
}

