package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllSongsPage extends BasePage{
    @FindBy(css = ".all-songs tr.song-item:nth-child(1)")
    private WebElement firstSongElementLocator;
    @FindBy(css = "li.playback")
    private WebElement choosePlayLocator;
    @FindBy(css = "[data-testid='sound-bar-play']")
    private WebElement soundBarVisualizer;

    public AllSongsPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean isSongPlaying() {
        findElement(soundBarVisualizer);
        return soundBarVisualizer.isDisplayed();
    }

    public AllSongsPage contextClickFirstSong() {
        contextClick(firstSongElementLocator);
        return this;
    }

    public AllSongsPage choosePlayOption() {
        choosePlayLocator.click();
        return this;
    }

}
