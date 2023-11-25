package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage{
    @FindBy(css = "img.avatar")
    private By avatar;
    @FindBy(css = "[name='current_password']")
    private WebElement currentPassword;
    @FindBy(css = "a.view-profile>span")
    private WebElement actualProfileName;
    @FindBy(css = "[name='name']")
    private WebElement profileName;
    @FindBy(css = "button.btn-submit")
    private WebElement saveButton;
    public ProfilePage(WebDriver givenDriver) {
        super(givenDriver);
    }
    public ProfilePage provideRandomProfileName(String randomName) {

        findElement(profileName);
        profileName.clear();
        profileName.sendKeys(randomName);
        return this;
    }

    public ProfilePage clickAvatar() {
        click(avatar);
        return this;
    }
    public void clickSave() {
        //type cast saveButton to By
        click((By) saveButton);
    }
    public ProfilePage getProfileName() {
        actualProfileName.getText();
        return this;
    }

    public ProfilePage provideCurrentPassword(String password) {
        findElement(currentPassword);
        currentPassword.clear();
        currentPassword.sendKeys(password);
        return this;
    }


}
