import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework21 extends BaseTest{
    @Test
    public void renamePlaylist() {
        //Precondition
        //login: email, password, submit
        provideEmail("fake@fakeaccount.com");
        providePassword("te$t$tudent");
        clickSubmit();

        //Actions
        choosePlaylist();
        editPlaylistName();
        clearOldName();
        rename();

        //Asserts
        checkVerificationMsg();


    }

    //helper methods*
    public void choosePlaylist()  {
        WebElement playlist =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"playlists\"]/ul/li[3]/a")));
        playlist.click();
        action.contextClick(playlist).perform();
    }
    public void editPlaylistName()  {
        WebElement editChoice =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"playlists\"]/ul/li[3]/nav/ul/li[1]")));
        editChoice.click();
    }
    public void clearOldName()  {
        WebElement textInput = wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector(" [name = 'name']"))));
        textInput.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));

    }
    public void rename(){
        WebElement renamePlaylist = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(" [name = 'name']")));
       renamePlaylist.sendKeys("newPlayList", Keys.ENTER);
    }
    public void checkVerificationMsg()  {
        WebElement popupNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.success.show")));
        Assert.assertTrue(popupNotification.isDisplayed());

    }
}
