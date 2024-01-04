package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage{
   @FindBy(xpath = "//input[@type='email']")
   private WebElement emailInput;
   @FindBy(xpath = "//input[@id='button']")
   private WebElement submitButton;
   @FindBy(xpath = "//div[text()= \"We've sent a confirmation link to the email. Please continue by clicking on it\"]")
   private WebElement confirmationMsg;
    public RegistrationPage(WebDriver givenDriver) {
        super(givenDriver);
    }
    public RegistrationPage provideEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }
    public void clickSubmit() {
        submitButton.click();
    }

    public boolean getConfirmationMsg() {
        return confirmationMsg.isEnabled();

    }
    public String confirmationMsgText() {
        return confirmationMsg.getText();
    }

    public String getValidationMsg() {
        return findElement(emailInput).getAttribute("validationMessage");
    }

}
