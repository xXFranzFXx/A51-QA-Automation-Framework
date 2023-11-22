package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage{
    //locators
    @FindBy(css = "[type='submit']")
    private WebElement submitButtonLocator;
    @FindBy(css = "[type='email")
    private WebElement emailField;
    @FindBy(css = "[type='password']")
    private WebElement passwordField;
    //constructor method
    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public LoginPage clickSubmitBtn() {
       submitButtonLocator.click();
       return this;
    }

    public LoginPage provideEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }
    public LoginPage providePassword(String password) {
       passwordField.sendKeys(password);
       return this;
    }
    public void  getRegistrationLink() {
        WebElement registrationLink = driver.findElement(By.linkText("Registration / Forgot password"));
        Assert.assertTrue(registrationLink.isDisplayed());
    }
}
