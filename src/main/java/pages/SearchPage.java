package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends BasePage{

        @FindBy(css = "section.artists > ul")
        private WebElement artistResult;
        public SearchPage(WebDriver givenDriver) {
            super(givenDriver);
        }

        public boolean artistExists() {
            return artistResult.isDisplayed();
        }
}
