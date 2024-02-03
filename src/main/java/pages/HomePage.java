package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import java.util.List;


public class HomePage extends BasePage {

    //user avatar icon element
    @CacheLookup
    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;
    @FindBy(css = "div.success.show")
    private WebElement successNotification;
    @FindBy(xpath = "//a[@href='/#!/profile']")
    private WebElement profilePageLink;
    @FindBy(xpath = "//div[@id='searchForm']/input[@type='search']")
    private WebElement searchSongInput;
    @FindBy(xpath = "//section[@id='playlists']//h1")
    private WebElement createNewPlaylistBtnLocator;
    @FindBy(xpath = "//section[@id='playlists']//i[@data-testid='sidebar-create-playlist-btn']")
    private WebElement createNewPlaylistBtn;
    @FindBy(xpath = "//nav[@class='menu playlist-menu']//li[@data-testid='playlist-context-menu-create-simple']")
    private WebElement contextMenuNewPlaylst;
    @FindBy(xpath = "//nav[@class='menu playlist-menu']//li[@data-testid='playlist-context-menu-create-smart']")
    private WebElement contextMenuNewSmartlst;
    @FindBy(css = "input[name='name']")
    private WebElement newPlaylistInput;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']")
    private WebElement smartListModal;
    @FindBy(xpath = "//span[@class=\"value-wrapper\"]/input[@type=\"text\"]")
    private WebElement smartListCriteriaInput;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']//input[@name='name']")
    private WebElement smartListFormNameInput;
    @FindBy(xpath = "//button[text()=\"Save\"]")
    private WebElement smartListSaveButton;
    @FindBy(xpath = "//section[@class='songs']//button[contains(.,'View All')]")
    private WebElement viewAllBtnLocator;
    @FindBy(css = ".search-results .virtual-scroller tr:nth-child(1)  .title")
    private WebElement firstSearchSong;
    @FindBy(css =".btn-add-to")
    private WebElement greenAddToBtn;
    @FindBy(css = "#songResultsWrapper li:nth-child(5)")
    private WebElement addToPlaylistMenuSelection;
    @FindBy(xpath = "//section[@id='playlists']/ul/li[3]/a")
    private WebElement playlistsMenuFirstPl;
    @FindAll({
            @FindBy(xpath = "//section[@id='playlists']/ul/li[@class='playlist playlist smart']/a"),
            @FindBy(xpath = "//section[@id='playlists']/ul/li[@class='playlist playlist']/a")
    })
    private List<WebElement> allPlaylists;
    @FindBy(xpath = "//section[@id='playlistWrapper']//table[@class='items']/tr")
    private List<WebElement> playlistSongs;
    @FindBy(xpath = "//div[@class='alertify']//nav/button[@class='ok']")
    private WebElement ok;
    private final By selectNewSmartList = By.cssSelector("li[data-testid=\"playlist-context-menu-create-smart\"]");

    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }


    public boolean getUserAvatar() {
        return userAvatarIcon.isEnabled();
    }
    public boolean notificationMsg() {
        findElement(successNotification);
        return successNotification.isDisplayed();
    }
    public HomePage searchSong(String song) {
        searchSongInput.clear();
        searchSongInput.sendKeys(song);
        return this;
    }
    public HomePage clickViewAllButton() {
        findElement(viewAllBtnLocator).click();
        return this;
    }
    public HomePage clickFirstSearchResult() {
        findElement(firstSearchSong).click();
        return this;
    }
    public HomePage clickCreateNewPlaylist() {
        actions.moveToElement(createNewPlaylistBtnLocator).perform();
        createNewPlaylistBtn.click();
        return this;
    }
    public HomePage clickGreenAddToBtn() {
        findElement(greenAddToBtn).click();
        return this;
    }

    public HomePage contextMenuNewPlaylist() {
        findElement(contextMenuNewPlaylst);
        actions.moveToElement(contextMenuNewPlaylst).perform();
        clickElement(contextMenuNewPlaylst);
        return this;
    }

    public HomePage contextMenuNewSmartlist() {
        actions.moveToElement(contextMenuNewSmartlst).perform();
        click(selectNewSmartList);
        return this;
    }

    public HomePage enterPlaylistName(String playlist) {
        WebElement input = findElement(newPlaylistInput);
        input.sendKeys(playlist);
        input.sendKeys(Keys.ENTER);
        return this;
    }
    public HomePage selectPlaylistToAddTo() {
        findElement(addToPlaylistMenuSelection).click();
        return this;
    }

    public boolean playlistAddedToMenu(String playlist) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='playlist playlist']/a[text()='" + playlist + "']"))).isDisplayed();
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
    public boolean playlistsEmpty() {
        return allPlaylists.isEmpty();
    }
    public boolean smartlistAddedToMenu(String playlist) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"playlist playlist smart\"]/a[text()='" + playlist + "']"))).isDisplayed();
    }

    public void clickSaveSmartList() {
        findElement(smartListSaveButton).click();
    }
    public HomePage contextClickFirstPlDelete() {
        By delete = By.xpath("//section[@id='playlists']/ul/li[3]/nav/ul/li[2]");
        contextClick(playlistsMenuFirstPl);
        click(delete);
        return this;
    }
    public HomePage deleteAllPlaylists() {
        List<WebElement> list = allPlaylists;
        if(list.isEmpty()) return this;
        for (WebElement l: list) {
            l.click();
            Reporter.log("playlists song total " + playlistSongs.size(), true);
            if(!playlistSongs.isEmpty()){
                contextClickFirstPlDelete();
                wait.until(ExpectedConditions.elementToBeClickable(ok)).click();
            } else {
                contextClickFirstPlDelete();
            }
        }
        return this;
    }
    public String getSearchInputValidationMsg() {
        return findElement(newPlaylistInput).getAttribute("validationMessage");
    }

}

