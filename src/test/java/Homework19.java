import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Homework19 extends BaseTest{
    @Test
    public void deletePlaylist() throws InterruptedException {
        //Unique playlist name
        String playlistName = "playlist";
        //verification message when selected playlist is deleted
        String expectedPlaylistDeletedMessage = "Deleted playlist /"+playlistName+./"";

        //navigate to login page
            //instead of using navigateToLoginPage method we will let the @Parameters decorator handle it

        //login: email, password, submit


        //open existing playlist*


        //delete the opened playlist*


        //Assertions: compare notification message with expected message*


    }

    //helper methods*

    //open existing playlist
    public void openPlaylist() {
        WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(2)"));
        emptyPlaylist.click();
    }

    //delete playlist
    public void clickDeletePlaylistButton() {
        WebElement deletePlaylist = driver.findElement(By.cssSelector(".btn-delete-playlist"));
        deletePlaylist.click();
    }

    //getDeletedPlaylistMsg
    public String getDeletedPlaylistMsg() {
        WebElement notificationMsg = driver.findElement(By.cssSelector("div.success.show"));
        return notificationMsg.getText();
    }


}
