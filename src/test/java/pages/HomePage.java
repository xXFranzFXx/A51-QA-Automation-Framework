package pages;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
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

    public void choosePlaylistByName(String playlistName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+playlistName+"')]"))).click();
    }

}
