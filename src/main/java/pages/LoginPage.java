package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    //locators
    @FindBy(css = "[type='submit']")
    private WebElement submitButtonLocator;
    @FindBy(css = "[type='email']")
    private WebElement emailField;
    @FindBy(css = "[type='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//a[@href='registration']")
    private WebElement registrationLinkLocator;
    @FindBy(css = ".fa-sign-out")
    private WebElement logoutBtn;
    //constructor method
    private By emailInput = By.cssSelector("[type='email']");
    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public void clickSubmitBtn() {
       submitButtonLocator.click();
    }
    public void enterEmail(String email) {
      driver.findElement(emailInput).sendKeys(email);

    }
    public LoginPage provideEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }
    public LoginPage providePassword(String password) {
       passwordField.sendKeys(password);
       return this;
    }
    public void loginValidCredentials() {
        provideEmail("franz.fernando+1@testpro.io");
        providePassword("te$t$tudent1");
        clickSubmitBtn();
    }
    public LoginPage clickLogOutBtn() {
        logoutBtn.click();
        return this;
    }
    public void clickRegistrationLink() {
        registrationLinkLocator.click();
    }
    public boolean getRegistrationLink() {
     WebElement registrationLink =  wait.until(ExpectedConditions.visibilityOf(registrationLinkLocator));
     return registrationLink.isDisplayed();

    }
}
