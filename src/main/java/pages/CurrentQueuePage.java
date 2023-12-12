package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CurrentQueuePage extends  BasePage{
    @FindBy(xpath = "//h1[text()[normalize-space()='Current Queue']]")
    private WebElement currentQueuePageTitle;

    @FindBy(css = "")
    private WebElement emptyListMsg;
    public CurrentQueuePage(WebDriver givenDriver) {
        super(givenDriver);
    }

}
