package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage{

    @FindBy(css = ".success.show")
    private WebElement updateNotification;
    @FindBy(css = ".error.show")
    private WebElement errorNotification;
    @FindBy(css = "button.btn-submit")
    private WebElement saveButton;

    public ProfilePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public ProfilePage clickSaveButton() {
       findElement(saveButton).click();
       return this;
    }
    public ProfilePage provideCurrentPassword(String password) {
        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#inputProfileCurrentPassword")));
        currentPassword.clear();
        currentPassword.sendKeys(password);
        return this;
    }
    public boolean notificationPopup() {
       WebElement notification = wait.until(ExpectedConditions.visibilityOf(updateNotification));
        return notification.isDisplayed();
    }
    public boolean errorNotificationPopup() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOf(errorNotification));
        return errorMsg.isDisplayed();
    }
    public String getErrorNotificationText() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOf(errorNotification));
        return errorMsg.getText();
    }

    public ProfilePage provideNewPassword(String newPassword) {
        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#inputProfileNewPassword")));
        currentPassword.clear();
        currentPassword.sendKeys(newPassword);
        return this;
    }

}
