import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework19 extends BaseTest{
    @Test
    public void deletePlaylist() throws InterruptedException {
        //Unique playlist name
        String uniquePlaylist = "1-HW19-Playlist";

        //verification message when selected playlist is deleted
        String deletePlaylistMsg = "Deleted playlist \"1-HW19-Playlist.\"";

        //navigate to login page
            //instead of using navigateToLoginPage method we will let the @Parameters decorator handle it

        //Precondition
        //login: email, password, submit
        provideEmail("fake@fakeaccount.com");
        providePassword("te$t$tudent");
        clickSubmit();
        Thread.sleep(2000);

        //create an empty playlist*
        createEmptyPlaylist(uniquePlaylist);
        Thread.sleep(4000);

        //open existing playlist*
        openPlaylist();
        Thread.sleep(2000);

        //delete the opened playlist*
        clickDeletePlaylistButton();
        Thread.sleep(2000);


        //Assertions: compare notification message with expected message*
        Assert.assertEquals(deletePlaylistMsg,getDeletedPlaylistMsg());


    }

    //helper methods*
    // invoke context menu
    private void clickContextMenu() {
        WebElement contextMenuSelection = driver.findElement(By.cssSelector("li[data-testid='playlist-context-menu-create-simple']"));
        contextMenuSelection.click();
    }
    //click on new playlist button
    private void clickNewPlaylistBtn() {
        WebElement addPlaylistBtn = driver.findElement(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']"));
        addPlaylistBtn.click();
    }
    //create empty playlist
    private void createEmptyPlaylist(String playlistName) {
        clickNewPlaylistBtn();
        clickContextMenu();
        WebElement playListField = driver.findElement(By.cssSelector("input[name='name']"));
        playListField.clear();
        playListField.sendKeys(playlistName);
        playListField.sendKeys(Keys.ENTER);
    }

    //open existing playlist
    private void openPlaylist() {
        WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(3)"));
        emptyPlaylist.click();
    }

    //delete playlist
    private void clickDeletePlaylistButton() {
        WebElement deletePlaylist = driver.findElement(By.cssSelector(".btn-delete-playlist"));
        deletePlaylist.click();
    }

    //getDeletedPlaylistMsg
    private String getDeletedPlaylistMsg() {
        WebElement notificationMsg = driver.findElement(By.cssSelector("div.success.show"));
        return notificationMsg.getText();
    }


}
