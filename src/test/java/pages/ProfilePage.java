package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage{
    @FindBy(xpath = "//*[@data-testid=\"btn-logout\"]/i")
    private WebElement logoutButton;
    @FindBy(css = ".success.show")
    private WebElement updateNotification;
    @FindBy(css = "[data-testid=\"view-profile-link\"] .name")
    private WebElement avatarLocator;
    @FindBy(xpath = "//*[@href=\"/#!/profile\"]")
    private WebElement profilePageLink;
    @FindBy(css = "[name='current_password']")
    private WebElement currentPassword;
    @FindBy(css = "[name='new_password']")
    private WebElement newPassword;
    @FindBy(xpath = "//*[@data-testid=\"view-profile-link\"] /span")
    private WebElement actualProfileName;

    @FindBy(css = "button.btn-submit")
    private WebElement saveButton;

    public ProfilePage(WebDriver givenDriver) {
        super(givenDriver);
    }
    public ProfilePage provideRandomProfileName(String randomName) {
        WebElement profileName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='name']")));
        profileName.clear();
        profileName.sendKeys(randomName);
        return this;
    }

    public ProfilePage clickAvatar() {
        findElement(profilePageLink).click();
        return this;
    }
    public Boolean clickSave() {
      findElement(saveButton).click();
      return updateNotification.isDisplayed();
    }
    public String getProfileName() {
       return findElement(actualProfileName).getText();
    }

    public ProfilePage provideCurrentPassword(String password) {
        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#inputProfileCurrentPassword")));
        currentPassword.clear();
        currentPassword.sendKeys(password);
        return this;
    }
    public Boolean notificationPopup() {
       WebElement notification = wait.until(ExpectedConditions.visibilityOf(updateNotification));
        return notification.isDisplayed();
    }
    public void clickLogout() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".success.show")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "//*[@data-testid=\"btn-logout\"]/i"))).click();
    }
    public ProfilePage provideNewPassword(String newPassword) {
        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#inputProfileNewPassword")));
        currentPassword.clear();
        currentPassword.sendKeys(newPassword);
        return this;
    }

}
