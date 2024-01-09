package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage{
    @FindBy(css = ".errors")
    private WebElement errors;
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
    //confirmation messages
    public boolean getConfirmationMsg() {
        return confirmationMsg.isEnabled();
    }
    public String confirmationMsgText() {
        return confirmationMsg.getText();
    }

    public String getValidationMsg() {
        return findElement(emailInput).getAttribute("validationMessage");
    }
    public String getPersonalEmailMsg() {
        return findElement(errors).getText();
    }

}
