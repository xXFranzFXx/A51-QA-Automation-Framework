import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework18 extends BaseTest {
    @Test
    public void playSong() {

    //navigate to test application login page
    navigateToLoginPage();

    //login: email, password, submit
    provideEmail("fake@fakeaccount.com");
    providePassword("te$t$tudent");
    clickSubmit();

    //play next button
    playNextSong();

    //play button
    clickPlay();

    //Assertions
    Assert.assertTrue(isSoundPlaying());

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
