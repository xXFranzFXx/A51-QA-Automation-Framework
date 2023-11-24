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
    @FindBy(xpath = "//a[@href='registration']")
    private WebElement registrationLink;
    @FindBy(css = ".fa-sign-out")
    private WebElement logoutBtn;
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
    public LoginPage loginValidCredentials() {
        provideEmail("fake@fakeaccount.com");
        providePassword("te$t$tudent");
        clickSubmitBtn();
        return this;
    }
    public LoginPage clickLogOutBtn() {
        logoutBtn.click();
        return this;
    }
    public WebElement getRegistrationLink() {
        return registrationLink;
    }
}
