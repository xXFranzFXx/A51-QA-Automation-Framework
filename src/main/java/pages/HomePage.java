package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeoutException;


public class HomePage extends BasePage {

    @FindBy(xpath = "//section[@id='playlists']//h1")
    private WebElement createNewPlaylistBtnLocator;
    @FindBy(xpath = "//section[@id='playlists']//i[@data-testid='sidebar-create-playlist-btn']")
    private WebElement createNewPlaylistBtn;
    @FindBy(css = "input[name='name']")
    private WebElement newPlaylistInput;
    @FindBy(xpath = "//section[@id='playlists']//ul/li[3]/a")
    private WebElement playlistsMenuFirstPl;
    @FindBy(xpath = "//section[@id='playlists']//li[@class='playlist playlist']/a")
    private List<WebElement> allPlaylists ;
    @FindBy(xpath="//section[@id='playlists']//ul/li[@class='playlist playlist smart']//li[text()[contains(.,'Delete')]]")
    private WebElement plDeleteBtn;
    @FindBy(xpath="//section[@id='playlists']//ul/li[@class='playlist playlist smart']//li[text()[contains(.,'Edit')]]")
    private WebElement plEditBtn;
    @FindBy(xpath = "//div[@class='alertify']//nav/button[@class='ok']")
    private WebElement ok;
    private By okBtn = By.xpath( "//div[@class='alertify']//nav/button[@class='ok']");
    @FindBy(xpath = "//section[@id='playlists']/ul/li")
    private List<WebElement> playlistsSection;
    @FindBy(xpath = "//nav[@class='menu playlist-menu']//li[@data-testid='playlist-context-menu-create-smart']")
    private WebElement contextMenuNewSmartlst;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']")
    private WebElement smartListModal;
    @FindBy(css = ".rule-group .btn-add-rule .fa.fa-plus")
    private WebElement smartListAddRuleBtn;
    @FindBy(css = ".btn-add-group .fa.fa-plus")
    private WebElement smartListAddGroupBtn;
    @FindBy(xpath = "//span[@class='value-wrapper']/input[@type='text']")
    private WebElement smartListCriteriaTextInput;
    @FindBy(xpath = "//span[@class='value-wrapper']/input[@type='number']")
    private WebElement smartListCriteriaIntInput;
    @FindBy(xpath = "//span[@class='value-wrapper']/input[@type='number']")
    private List<WebElement> smartListCriteriaIntInputGrp;
    @FindBy(xpath = "//span[@class='value-wrapper']/input[@type='date']")
    private WebElement smartListCriteriaDateInput;
    @FindBy(xpath = "//span[@class='value-wrapper']/input[@type='text']")
    private List<WebElement> smartListCriteriaInputGroup;
    @FindBy(css = "li.smart")
    private List<WebElement> sideMenuSmartPlaylists;
    @FindBy(css = "#playlists li.smart a")
    private List<WebElement> sideMenuSmartPlaylistName;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']//input[@name='name']")
    private WebElement smartListFormNameInput;
    @FindBy(xpath = "//form[@data-testid='edit-smart-playlist-form']//input[@name='name']")
    private WebElement editSmartListFormNameInput;
    @FindBy(xpath = "//button[text()='Save']")
    private WebElement smartListSaveButton;
    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement smartListCancelButton;
    @FindBy(xpath = "//section[@id='playlists']/ul/li[@class='playlist playlist smart']/a")
    private List<WebElement> allSmartPlaylists;
    @FindBy(xpath = "//div[@data-test='smart-playlist-rule-row']//select[@name='model[]']")
    private WebElement smartListModelSelectMenu;
    @FindBy(xpath = "//div[@data-test='smart-playlist-rule-row']//select[@name='operator[]']")
    private WebElement smartListOperatorSelectMenu;
    @FindBy(css = ".fa.fa-file-o")
    private WebElement emptySmartlistIcon;
    @FindBy(css ="#playlistWrapper .items")
    private List<WebElement> smartPlaylistSongs;
    @FindBy(css = "#playlistWrapper .text")
    private WebElement emptySmartListText;



    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean checkEmptySmartListIcon() {
        return emptySmartlistIcon.isDisplayed();
    }
    public String emptySmartListMessage() {
        return emptySmartListText.getText();
    }
    public String getNameInputValidationMsg() {
        return findElement(smartListFormNameInput).getAttribute("validationMessage");
    }
    public String getCriteriaInputValidationMsg() {
        return findElement(smartListCriteriaTextInput).getAttribute("validationMessage");
    }
    public HomePage clickCreateNewPlaylist() {
        actions.moveToElement(createNewPlaylistBtnLocator).perform();
        createNewPlaylistBtn.click();
        return this;
    }
    public HomePage selectOperatorOption(String option){
        Select operatorSelectMenu = new Select(smartListOperatorSelectMenu);
        operatorSelectMenu.selectByVisibleText(option);
        return this;
    }
    public HomePage selectModelOption(String option){
        Select modelSelectMenu = new Select(smartListModelSelectMenu);
        modelSelectMenu.selectByVisibleText(option);
        return this;
    }
    public boolean checkSmartListEmpty() {
        return smartPlaylistSongs.isEmpty();
    }
    public HomePage enterSmartListDateCriteria(String date) {
        smartListCriteriaDateInput.sendKeys(date);
        return this;
    }

    public boolean playlistsEmpty() {
        return (playlistsSection.size() == 2);
    }

    public void checkOkModal() {
        List<WebElement> ele2 = driver.findElements(okBtn);
        if (!ele2.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(findElement(ok)));
            actions.moveToElement(ok).click().pause(1000).perform();
        } else {
            deleteAllPlaylists();
        }
    }
    public HomePage deleteAllPlaylists() {
        try {
            for (int i = 2; i < playlistsSection.size(); i ++) {
//                clickElement(playlistsSection.get(i));
                contextClick(findElement(playlistsSection.get(i)));
                actions.moveToElement(plDeleteBtn).click().pause(1000).perform();
                checkOkModal();
            }
        } catch (StaleElementReferenceException e) {
          e.printStackTrace();
        }
        return this;
    }

    public HomePage contextMenuNewSmartlist() {
        actions.moveToElement(contextMenuNewSmartlst).perform();
        clickElement(contextMenuNewSmartlst);
        pause(1);
        return this;
    }
    public boolean smartListModalVisible() {
        return findElement(smartListModal).isDisplayed();
    }
    public HomePage enterSmartListName(String smartList) {
        WebElement input = findElement(smartListFormNameInput);
        input.sendKeys(smartList);
        return this;
    }

    public HomePage enterSmartListTextCriteria(String criteria) {
        WebElement input = findElement(smartListCriteriaTextInput);
        input.sendKeys(criteria);
        return this;
    }
    public HomePage enterSmartListIntCriteria(int integer) {
        WebElement input = findElement(smartListCriteriaIntInput);
        input.sendKeys(String.valueOf(integer));
        return this;
    }
    public boolean smartlistAddedToMenu (String playlist){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='playlist playlist smart']/a[text()='" + playlist + "']"))).isDisplayed();
    }
    public HomePage clickSaveSmartList() {
        findElement(smartListSaveButton).click();
        wait.until(ExpectedConditions.invisibilityOf(ok));
//        pause(1);
        return this;
    }
    public HomePage clickGroupRuleBtn() {
        findElement(smartListAddGroupBtn).click();
        return this;
    }
    public HomePage enterGroupRulesText(String[] text){
        for (int i = 0; i < text.length; i ++) {
            smartListCriteriaInputGroup.get(i).sendKeys(text[i]);
        }
        return this;
    }
    public HomePage enterIntCriteria(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            smartListCriteriaIntInputGrp.get(i).sendKeys(String.valueOf(ints[i]));
        }
        return this;
    }
        public HomePage clickSmartListCancelBtn() {
        findElement(smartListCancelButton).click();
        return this;
    }
    public HomePage cmEditFirstSmartPl() {
        contextClick(sideMenuSmartPlaylistName.get(0));
        findElement(plEditBtn).click();
        return this;
    }
    public String getFirstSmartPlName() {
        return sideMenuSmartPlaylistName.get(0).getText();
    }
    public HomePage editSmartPlName(String playlist){
        WebElement input = findElement(editSmartListFormNameInput);
        input.click();
        input.sendKeys(playlist);
        return this;
    }
}

