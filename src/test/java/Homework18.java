import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Homework18 extends BaseTest {
    @Test
    public void playSong() {

    //launch browser

    //login: email, password, submit

    //play next button

    //play button

    //Assertions
    }
    /**************************    helper methods  *************************************************/
    //check if soundBar element is displayed
    private boolean isSoundPlaying() {
        WebElement soundBar = driver.findElement(By.cssSelector("[data-testid='sound-bar-play']"));
        //boolean requires return value, check if element is displayed
        return soundBar.isDisplayed();
    }
    //click play button
    public void clickPlay() {
        WebElement playButton = driver.findElement(By.cssSelector("[data-testid='play-btn']"));
        playButton.click();
    }
    //click play next button
    private void playNextSong() {
        WebElement playNextButton = driver.findElement(By.cssSelector("[data-testid='play-next-btn']"));
        playNextButton.click();
    }

}
