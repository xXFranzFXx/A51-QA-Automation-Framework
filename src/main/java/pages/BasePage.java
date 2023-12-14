package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Set;

//POM
public class BasePage {
    //POM attributes
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    private int timeSeconds = 5;
    public static Set<String> themes = Set.of("pines","classic", "violet", "oak", "slate", "madison", "astronaut", "chocolate", "laura", "rose-petals", "purple-waves", "pop-culture", "jungle", "mountains", "nemo", "cat");
    @FindBy(css = "button[data-testid='about-btn']")
    private WebElement aboutBtnLocator;
    @FindBy(css = "[data-testid='about-modal']")
    private WebElement aboutModalLocator;
//    @FindBy(css = "button[data-testid='about-btn']")
    @FindBy(xpath = "//*[@class=\"modal-wrapper overlay\"]//footer")
    private WebElement modalCloseLocator;
    @FindBy(xpath = "//button[@data-test='close-modal-btn']")
    private WebElement closeModalBtn;
    private By closeModalButton = By.xpath("//*[@class=\"modal-wrapper overlay\"]//footer//button");

    @FindBy(css = "a[class='queue']")
    private WebElement currentQueueLocator;
    @FindBy(css = "a[class='home active']")
    private WebElement homeLocator;
    @FindBy(css = "a[class='songs']")
    private WebElement allSongsLocator;
    @FindBy(css = "a[class='albums']")
    private WebElement albumsLocator;
    @FindBy(css = "a[class='artists']")
    private WebElement artistsLocator;
    @FindBy(xpath = "//*[@class='playlist favorites']//a")
    private WebElement favoritesLocator;
    @FindBy(css = "#overlay.overlay.loading")
    private WebElement sideMenuOverlayLoading;
    @FindBy(css = "[data-testid='sound-bar-play']")
    private WebElement soundBarVisualizer;
    @FindBy(xpath = "//*[@class='playlist recently-played']")
    private WebElement recentlyPlayedLocator;
    @FindBy(xpath = "//*[@id=\"searchExcerptsWrapper\"]/div/div/section[1]/ul/article/span[2]/span[1]")
    private WebElement searchResultSongLocator;
    //constructor method
    public BasePage(WebDriver givenDriver) {
        driver = givenDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    //reusable methods, inherited by other pages

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
    public boolean verifyTheme (String theme) {
       return themes.contains(theme) ? wait.until(ExpectedConditions.attributeToBe(By.xpath("//html[@data-theme]"), "data-theme", theme)) : false;
    }
    protected void currentQueuePage () {
        actions.moveToElement(currentQueueLocator).perform();
        clickElement(currentQueueLocator);    }
    protected void recentlyPlayedPage() {
        actions.moveToElement(recentlyPlayedLocator).perform();
            clickElement(recentlyPlayedLocator);
    }
    protected void artistsPage() {
        actions.moveToElement(artistsLocator).perform();
        clickElement(artistsLocator);    }
    protected void albumsPage() {
        actions.moveToElement(albumsLocator).perform();
        clickElement(albumsLocator);
    }
    protected void allSongsPage() {  actions.moveToElement(allSongsLocator).perform();
        clickElement(allSongsLocator);
    }
    protected void homePage() {  actions.moveToElement(homeLocator).perform();
        clickElement(homeLocator);
    }
    protected void about() { actions.moveToElement(aboutBtnLocator).perform();
            clickElement(aboutBtnLocator);
    }
    protected void favorites() {actions.moveToElement(favoritesLocator).perform();
        clickElement(favoritesLocator);
    }

    protected boolean checkAboutModal() {
        return findElement(aboutModalLocator).isDisplayed();
    }
    protected String getSearchedSongTitle() {
            return searchResultSongLocator.getText();
        }
    protected void closeModal() {
       actions.moveToElement(modalCloseLocator).perform();
       click(closeModalButton);

    }
    protected boolean modalIsClosed() {
        return wait.until(ExpectedConditions.invisibilityOf(closeModalBtn));
    }



}
