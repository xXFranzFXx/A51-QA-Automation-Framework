package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllSongsPage extends BasePage{
    @FindBy(css = ".all-songs tr.song-item:nth-child(1)")
    WebElement firstSongElement;
    @FindBy(css = "li.playback")
    WebElement choosePlay;
    @FindBy(css = "[data-testid='sound-bar-play']")
    WebElement soundBarVisualizer;

    public AllSongsPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean isSongPlaying() {
        findElement(soundBarVisualizer);
        return soundBarVisualizer.isDisplayed();
    }

    public AllSongsPage contextClickFirstSong() {
        contextClick(firstSongElement);
        return this;
    }

    public AllSongsPage choosePlayOption() {
        click(choosePlay);
        return this;
    }

}
