package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

//POM
public class BasePage {
    //POM attributes
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    private int timeSeconds = 10;

    //constructor method
    public BasePage(WebDriver givenDriver) {
        driver = givenDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeSeconds));
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
//        PageFactory.initElements(new AjaxElementLocatorFactory(driver, timeSeconds), this);
    }

    //reusable methods, inherited by other pages

    protected WebElement findElement(WebElement webElement) {
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    protected List<WebElement> findElements(By locator) {
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        List<WebElement> elements = driver.findElements(locator);
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return elements;
    }
    protected void contextClick(WebElement webElement) {
        actions.contextClick(findElement(webElement)).perform();
    }
    protected void doubleClick(WebElement webElement) {
        actions.doubleClick(findElement(webElement)).perform();
    }
}
