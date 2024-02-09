package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistsPage extends BasePage{

    @FindBy(css = ".artists .thumbnail-wrapper .cover > a")
    private List<WebElement> artistPlayBtns;
    @FindBy(xpath = "//img[@alt='Sound bars']")
    private WebElement soundBarLocator;
    @FindBy(css = "#artistsWrapper article.item.full")
    private List<WebElement> artistTiles;
    public ArtistsPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean artistsArePresent() {
        return !artistTiles.isEmpty();
    }
    public void invokeSearchFromKeybd() {
        wait.until(ExpectedConditions.urlContains("https://qa.koel.app/#!"));
        actions.sendKeys("f").perform();
    }
    public ArtistsPage searchArtist(String artist) {
        invokeSearchFromKeybd();
        actions.pause(2).sendKeys(artist).perform();
        return this;
    }
    public ArtistsPage clickArtistPlay() {
        findElement(artistPlayBtns.get(0)).click();
        return this;
    }
    public boolean songIsPlaying() {
        return findElement(soundBarLocator).isDisplayed();
    }
    public List<String> getArtistsNames() {
        List<String> names = new ArrayList<>();
        pause(4);
        for(WebElement artist: artistTiles) {
            String artistName = findElement(artist).getAttribute("title");
            names.add(artistName);
        }
        //return names.stream().map(String::valueOf).collect(Collectors.joining(", "));
        return names;
    }
}
