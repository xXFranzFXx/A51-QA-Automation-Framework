package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//POM
public class BasePage {
    //POM attributes
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    //constructor method
    public BasePage(WebDriver givenDriver) {
        driver = givenDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
        actions = new Actions(driver);
    }

    //reusable methods, inherited by other pages

    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
