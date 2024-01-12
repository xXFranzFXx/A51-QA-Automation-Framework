package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class AllSongsPage extends BasePage{

    @FindBy(xpath = "//section[@id='songsWrapper']//tr[@class='song-item']//button[@class='text-secondary' and contains(@title, 'Like')]")
    private WebElement singleLikeButton;
    @FindBy(xpath = "//section[@id='songsWrapper']//tr[@class='song-item']//button[@class='text-secondary' and contains(@title, 'Unlike')]")
    private List<WebElement> likedSongsButton;

    public AllSongsPage(WebDriver givenDriver) {
        super(givenDriver);
    }


    //unlikes every liked song
    public AllSongsPage unlikeSongs() {
        if(likedSongsButton.isEmpty()) return this;
        for(WebElement l: likedSongsButton) {
                l.click();
        }
        return this;
    }


    public AllSongsPage likeOneSong() {
       findElement(singleLikeButton).click();
       return this;
    }

}
