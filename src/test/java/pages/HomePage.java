package pages;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    //user avatar icon element
    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;

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
    @FindBy(css = ".btn-delete-playlist")
    private By deletePlaylistBtn;
    //notification message
    @FindBy(css = ".all-songs tr.song-item:nth-child(1)")
    private WebElement firstSongLocator;
    @FindBy(css =".show-success")
    private WebElement successNotification;
    //block containing all songs
    @FindBy(css= ".songs")
    private WebElement allSongs;
    @FindBy(css =  "[data-testid='playlist-context-menu-create-simple']")
    private WebElement selectCreateNewPlaylist;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/input")
    private WebElement searchSongInput;

    //play button used for hoverplay method
    @FindBy(css = "[data-testid='play-btn']")
    private WebElement play;
    @FindBy(css = "section#playlistWrapper td.title")
    private By songTitle;
    @FindBy(css = "button[data-test='view-all-songs-btn']")
    private By viewAllBtnLocator;

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
    @FindBy(xpath = "//*[@id=\"searchExcerptsWrapper\"]/div/div/section[1]/ul/article/span[2]/span[1]/span/a/text()")
    private WebElement searchResultArtistText;
    /**
     * Search results components end
     */

    /**
     * INFO panel components start
     */
    @FindBy(css = "[data-testid='extra-panel']")
    private WebElement infoPanel;
    @FindBy(css = "[data-testid='toggle-extra-panel-btn]")
    private WebElement infoButton;
    @FindBy(css = ".control.text-uppercase.active")
    private WebElement infoButtonActive;
    @FindBy(xpath = "//*[@id='extraTabLyrics']")
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


    /**
     * INFO panel components end
     */
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean getUserAvatar () {
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
    public boolean hoverPlay() throws InterruptedException{
        findElement(play);
        actions.moveToElement(play).perform();
        return play.isDisplayed();

    }
    public int countSongs() {
       return findElements(songTitle).size();
    }

    public HomePage choosePlaylistByName(String playlistName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+playlistName+"')]"))).click();
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
    public HomePage clickFirstSearchResult() {
        click(firstSongResult);
        return this;
    }
    public HomePage clickGreenAddToBtn() {
        click(greenAddToBtn);
        return this;
    }


}
