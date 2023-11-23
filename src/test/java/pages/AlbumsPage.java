package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AlbumsPage extends BasePage{

    //locators

    //element to play all songs in an album
    @FindBy(xpath = "//li[text()='Play All']")
    WebElement playAll;

    //element to shuffle all current songs playing in the album
    @FindBy(css = "li[data-test='shuffle']")
    WebElement shuffleSongs;



    public AlbumsPage(WebDriver givenDriver) {
        super(givenDriver);
    }
}
