package pages;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//POM
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    @FindBy(xpath = "//nav[@id='sidebar']//a[@class='songs']")
    @CacheLookup
    private WebElement allSongsLocator;
    @FindBy(xpath = "//nav[@id='sidebar']//a[@class='artists']")
    private WebElement artistsLocator;
    @FindBy(xpath = "//section[@id='playlists']//li[@class='playlist favorites']/a")
    @CacheLookup
    private WebElement favoritesLocator;

    @FindBy(css = "[data-testid='sound-bar-play']")
    @CacheLookup
    private WebElement soundBarVisualizer;

    public BasePage(WebDriver givenDriver) {
        driver = givenDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement findElement(WebElement webElement) {
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }
    protected void clickElement(WebElement locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    protected List<WebElement> findElements(By locator) {
        List<WebElement> elements;
        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return elements;
    }
   protected boolean textIsPresent(By locator, String text) {
       return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    protected void contextClick(WebElement webElement) {
        actions.contextClick(findElement(webElement)).perform();
    }
    protected void doubleClick(WebElement webElement) {
        actions.doubleClick(findElement(webElement)).perform();
    }
    protected boolean isSongPlaying() {
        findElement(soundBarVisualizer);
        return soundBarVisualizer.isDisplayed();
    }


    public ArtistsPage navigateToArtistsPage() {
        actions.moveToElement(artistsLocator).perform();
        clickElement(artistsLocator);
        return new ArtistsPage(driver);
    }
    public void pause(int seconds) {
        actions.pause(Duration.ofSeconds(seconds)).perform();
    }

}
