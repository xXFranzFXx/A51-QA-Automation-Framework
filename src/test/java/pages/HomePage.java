package pages;

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
    private WebElement firstPlaylist;
    //playlist name input field, accessed when creating a new playlist
    @FindBy(css = "input[name='name']")
    private WebElement playlistInputField;
    //"plus" icon for creating new playlist
    @FindBy(css = ".fa-plus-circle")
    private WebElement addNewPlaylistIcon;
    //delete playlist button
    @FindBy(css = ".btn-delete-playlist")
    private WebElement deletePlaylistBtn;
    //notification message
    @FindBy(css =".show-success")
    private WebElement successNotification;
    //block containing all songs
    @FindBy(css= ".songs")
    private WebElement allSongs;
    @FindBy(css =  "[data-testid='playlist-context-menu-create-simple']")
    private WebElement selectCreateNewPlaylist;
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean getUserAvatar () {
        return userAvatarIcon.isEnabled();
    }
    public HomePage chooseFirstPlaylist() {
        firstPlaylist.click();
        return this;
    }
    public HomePage deletePlaylist() {
        deletePlaylistBtn.click();
        return this;
    }
    public boolean notificationMsg() {
        findElement(successNotification);
        return successNotification.isDisplayed();
    }
}
