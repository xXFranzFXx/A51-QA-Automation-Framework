package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeoutException;


public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@id='searchForm']/input[@type='search']")
    private WebElement searchSongInput;
    @FindBy(xpath = "//section[@id='playlists']//h1")
    private WebElement createNewPlaylistBtnLocator;
    @FindBy(xpath = "//section[@id='playlists']//i[@data-testid='sidebar-create-playlist-btn']")
    private WebElement createNewPlaylistBtn;
    @FindBy(xpath = "//nav[@class='menu playlist-menu']//li[@data-testid='playlist-context-menu-create-simple']")
    private WebElement contextMenuNewPlaylst;
    @FindBy(css = "input[name='name']")
    private WebElement newPlaylistInput;
    @FindBy(xpath = "//section[@class='songs']//button[contains(.,'View All')]")
    private WebElement viewAllBtnLocator;
    @FindBy(css = ".search-results .virtual-scroller tr:nth-child(1)  .title")
    private WebElement firstSearchSong;
    @FindBy(css =".btn-add-to")
    private WebElement greenAddToBtn;
    @FindBy(css = "#songResultsWrapper li:nth-child(5)")
    private WebElement addToPlaylistMenuSelection;
    @FindBy(xpath = "//section[@id='playlists']//ul/li[3]/a")
    private WebElement playlistsMenuFirstPl;
  @FindBy(xpath = "//section[@id='playlists']//li[@class='playlist playlist']/a")
    private List<WebElement> allPlaylists ;
//    @FindBy(xpath = "//section[@id='playlists']/ul/li[3]/nav/ul/li[2]")
    @FindBy(xpath="//section[@id='playlists']//ul/li[@class='playlist playlist']//li[text()[contains(.,'Delete')]]")
    private WebElement plDeleteBtn;
    @FindBy(xpath = "//div[@class='alertify']//nav/button[@class='ok']")
    private WebElement ok;
    private By okBtn = By.xpath( "//div[@class='alertify']//nav/button[@class='ok']");
    @FindBy(xpath = "//section[@id='playlists']/ul/li")
    private List<WebElement> playlistsSection;

    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
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
    public boolean playlistsEmpty() {
        return (playlistsSection.size() == 2);
    }
    public boolean playlistAddedToMenu(String playlist) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='playlist playlist']/a[text()='" + playlist + "']"))).isDisplayed();
    }

    public void checkOkModal() {
        List<WebElement> ele2 = driver.findElements(okBtn);
        if (!ele2.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(findElement(ok)));
            actions.moveToElement(ok).click().pause(1000).perform();
        } else {
            deleteAllPlaylists();
        }
    }
    public HomePage contextClickFirstPlDelete() {
        WebElement firstPl = wait.until(ExpectedConditions.elementToBeClickable(playlistsMenuFirstPl));
        contextClick(findElement(firstPl));
        wait.until(ExpectedConditions.elementToBeClickable(plDeleteBtn)).click();
        checkOkModal();
        return this;
    }
    public HomePage deleteAllPlaylists() {
        try {
            for (int i = 2; i < playlistsSection.size(); i ++) {
//                clickElement(playlistsSection.get(i));
                contextClick(findElement(playlistsSection.get(i)));
                actions.moveToElement(plDeleteBtn).click().pause(1000).perform();
                checkOkModal();
            }
        } catch (StaleElementReferenceException e) {
          e.printStackTrace();
        }
        return this;
    }
    public String getSearchInputValidationMsg() {
        return findElement(newPlaylistInput).getAttribute("validationMessage");
    }

}

