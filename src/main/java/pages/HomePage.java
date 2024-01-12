package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

    //user avatar icon element
    @CacheLookup
    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;

    @FindBy(xpath = "//a[@href='/#!/profile']")
    private WebElement profilePageLink;
    @FindBy(xpath = "//div[@id='searchForm']/input[@type='search']")
    private WebElement searchSongInput;


    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }


    public boolean getUserAvatar() {
        return userAvatarIcon.isEnabled();
    }
    public void searchSong(String song) {
        searchSongInput.clear();
        searchSongInput.sendKeys(song);
    }

    public void clickAvatar() {
        findElement(profilePageLink).click();
        new ProfilePage(driver);
    }

}

