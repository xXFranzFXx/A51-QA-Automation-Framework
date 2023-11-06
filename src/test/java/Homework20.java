import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework20 extends BaseTest{
    @Test
    public void deletePlaylist() {
        String newPlaylistName = "playlist";
        //verification message when selected playlist is deleted
        String deletePlaylistMsg = "Deleted playlist \"playlist.\"";

        //navigate to login page
        //instead of using navigateToLoginPage method we will let the @Parameters decorator handle it

        //Precondition
        //login: email, password, submit
        provideEmail("fake@fakeaccount.com");
        providePassword("te$t$tudent");
        clickSubmit();
//        Thread.sleep(2000);

        //create an empty playlist*
//        createEmptyPlaylist(uniquePlaylist);
//        Thread.sleep(4000);

        //open existing playlist*
        openPlaylist();
//        Thread.sleep(2000);

        //delete the opened playlist*
        clickDeletePlaylistButton();
//        Thread.sleep(2000);


        //Assertions: compare notification message with expected message*
        Assert.assertEquals(deletePlaylistMsg,getDeletedPlaylistMsg());

    }

    //helper methods*
    // invoke context menu
    private void clickContextMenu() {
//        WebElement contextMenuSelection = driver.findElement(By.cssSelector("li[data-testid='playlist-context-menu-create-simple']"));
        WebElement  contextMenuSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[data-testid='playlist-context-menu-create-simple']")));
        contextMenuSelection.click();
    }
    //click on new playlist button
    private void clickNewPlaylistBtn() {
//        WebElement addPlaylistBtn = driver.findElement(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']"));
        WebElement addPlaylistBtn =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")));
        addPlaylistBtn.click();
    }
    //create empty playlist
    private void createEmptyPlaylist(String playlistName) {
        clickNewPlaylistBtn();
        clickContextMenu();
//        WebElement playListField = driver.findElement(By.cssSelector("input[name='name']"));
        WebElement playListField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
        playListField.sendKeys(playlistName);
        playListField.sendKeys(Keys.ENTER);
    }

    //open existing playlist
    private void openPlaylist() {
//        WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(3)"));
        WebElement emptyPlaylist = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".playlist:nth-child(3)")));
        emptyPlaylist.click();
    }

    //delete playlist
    private void clickDeletePlaylistButton() {
//        WebElement deletePlaylist = driver.findElement(By.cssSelector(".btn-delete-playlist"));
        WebElement deletePlaylist = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Delete this playlist']")));
        deletePlaylist.click();
    }

    //getDeletedPlaylistMsg
    private String getDeletedPlaylistMsg() {
//        WebElement notificationMsg = driver.findElement(By.cssSelector("div.success.show"));
        WebElement notificationMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.success.show")));
        return notificationMsg.getText();
    }


}

