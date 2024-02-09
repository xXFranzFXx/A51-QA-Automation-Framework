package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    //locators
    @CacheLookup
    @FindBy(css = "[type='submit']")
    private WebElement submitButtonLocator;
    @CacheLookup
    @FindBy(css = "[type='email']")
    private WebElement emailField;
    @CacheLookup
    @FindBy(css = "[type='password']")
    private WebElement passwordField;

    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public void clickSubmitBtn() {
       submitButtonLocator.click();
    }

    public LoginPage provideEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }
    public LoginPage providePassword(String password) {
       passwordField.sendKeys(password);
       return this;
    }
    public HomePage loginValidCredentials() {
        provideEmail(System.getProperty("koelUser"));
        providePassword(System.getProperty("koelPassword"));
        clickSubmitBtn();
        return new HomePage(driver);
    }


}
