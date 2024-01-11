package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends BasePage{
    @FindBy(xpath = "//section[@data-testid='song-excerpts']//p")
    private WebElement noSongsText;

    @FindBy(css ="#searchForm input[type='search']")
    private WebElement searchInputLocator;
    @FindBy(xpath = "#searchExcerptsWrapper div[@class='text']")
    private WebElement emptyResultsPlaceholder;
    @FindBy(xpath = "//section[data-testid='song-excerpts']//ul")
    private WebElement songResult;
    @FindBy(xpath = "//section[data-testid='artist-excerpts']//ul")
    private WebElement artistResult;
    @FindBy(xpath = "//section[data-testid='album-excerpts']//ul")
    private WebElement albumResult;


    public SearchPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean isSearchResultEmpty(String section) {
        By childLocator = By.xpath("//section[@data-testid='"+section+"-excerpts']//article");
        List<WebElement> results = findElements(childLocator);
        return results.isEmpty();
    }

    public boolean noneFoundTextExists(String section) {
        List<WebElement> text = findElements(By.xpath("//section[@data-testid='"+section+"-excerpts']//p"));
        return text != null;
    }
    public WebElement getShadowCancelBtn() {

//        WebElement host = this.driver.findElement(By.xpath("//div[@id='searchForm']//input"));
       SearchContext shadowRoot = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='searchForm']//input"))).getShadowRoot();
        return shadowRoot.findElement(By.cssSelector("div [@role='button']"));
    }
    public WebElement getSearchPlaceholderTxt() {
        WebElement root = findElement(searchInputLocator);

        WebElement shadow = getShadowDOM(root, this.driver);
        return shadow.findElement(By.cssSelector("div [@pseudo='placeholder']"));
    }
    public SearchContext expandRootElement (WebElement webElement) {
        return (SearchContext) javascriptExecutor.executeScript("return arguments[0].shadowRoot", webElement);
    }
    public WebElement getShadowDOM(WebElement webElement, WebDriver givenDriver) {
        return (WebElement) javascriptExecutor.executeScript("return arguments[0].shadowRoot", webElement);
    }
}
