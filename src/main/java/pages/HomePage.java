package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;


public class HomePage extends BasePage {
    @CacheLookup
    @FindBy(css = "#playlists i.fa.fa-plus-circle.create")
    private WebElement createNewPlaylistBtn;
    @FindBy(xpath="//section[@id='playlists']//ul/li[@class='playlist playlist smart']//li[text()[contains(.,'Delete')]]")
    private WebElement plDeleteBtn;
    @FindBy(xpath="//section[@id='playlists']//ul/li[@class='playlist playlist smart']//li[text()[contains(.,'Edit')]]")
    private WebElement plEditBtn;
    @FindBy(xpath = "//div[@class='alertify']//nav/button[@class='ok']")
    private WebElement ok;
    @FindBy(xpath = "//section[@id='playlists']/ul/li")
    private List<WebElement> playlistsSection;
    @FindBy(xpath = "//nav[@class='menu playlist-menu']//li[@data-testid='playlist-context-menu-create-smart']")
    private WebElement contextMenuNewSmartlst;
    @FindBy(xpath = "//form[@data-testid='create-smart-playlist-form']")
    private WebElement smartListModal;
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
    private By okBtn = By.xpath( "//div[@class='alertify']//nav/button[@class='ok']");


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
        pause(2);
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(createNewPlaylistBtn));
       findElement(plusButton).click();
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
        pause(2);
        return (playlistsSection.size() == 2);
    }

    public void checkOkModal() {
        List<WebElement> ele2 = driver.findElements(okBtn);
        if (!ele2.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(findElement(ok)));
            actions.moveToElement(ok).click().pause(2).perform();
        } else {
            pause(1);
            deleteAllPlaylists();
        }
    }
    public HomePage deleteAllPlaylists() {
        int count = 0;
        try {
            System.out.println(playlistsSection.size());
            for (int i = 2; i < playlistsSection.size(); i ++) {
                WebElement plSection = wait.until(ExpectedConditions.elementToBeClickable(playlistsSection.get(i)));
                actions.moveToElement(plSection).pause(1).perform();
                contextClick(plSection);
//                contextClick(playlistsSection.get(i));
                actions.moveToElement(plDeleteBtn).click().pause(3).perform();
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
        pause(2);
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
        if(!isEditModalVisible()) pause(2);
        WebElement input = findElement(editSmartListFormNameInput);
        actions.moveToElement(input).doubleClick(input).perform();
        input.sendKeys(Keys.SPACE);
        pause(2);
        input.sendKeys(playlist);
        return this;
    }
    public boolean isEditModalVisible() {
        WebElement editModal = find(By.cssSelector(".smart-playlist-form form"));
        return  editModal.isDisplayed();
    }
}

