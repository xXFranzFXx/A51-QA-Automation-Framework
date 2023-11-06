package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
    //locators
    By submitButtonLocator = By.cssSelector("[type='submit']");
    By emailField = By.cssSelector("[type='email");
    By passwordField = By.cssSelector("[type='password']");
    //constructor method
    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public void clickSubmitBtn() {
        driver.findElement(submitButtonLocator).click();
    }

    public void provideEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }
    public void providePassword(String password) {
       driver.findElement(passwordField).sendKeys(password);
    }
}
