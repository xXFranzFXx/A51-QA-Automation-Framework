package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumsPage extends BasePage{
    //Albums side bar menu choice
    @FindBy(xpath = "//li[text()='Play All']")
    private WebElement playAll;
    //element to shuffle all current songs playing in the album
    @FindBy(xpath = "//section[@id='albumsWrapper']/div/article[1]/span/span/a")
    private WebElement firstAlbumLocator;
    @FindBy(xpath = "//h1[text()[normalize-space()='Albums']]")
    private WebElement albumsPageTitleLocator;
    @FindBy(css = "#albumsWrapper article.item.full")
    private List<WebElement> albumTiles;
    @FindBy(css="#albumsWrapper article.item.full span.cover")
    private List<WebElement> albumCoverPics;
    @FindBy(css ="#albumsWrapper article.item.full footer .info .name")
    private List<WebElement> albumTitles;
    @FindBy(css = "#albumsWrapper article.item.full footer .info .artist")
    private List<WebElement> albumArtists;
    @FindBy(css="#albumsWrapper footer p.meta")
    private List <WebElement> footerInfoContainer;
    @FindBy(css = "#albumsWrapper p.meta i.fa.fa-download")
    private List <WebElement> downloadBtn;
    @FindBy(css = "#albumsWrapper p.meta i.fa.fa-random")
    private List <WebElement> shuffleBtn;
    @FindBy(css = "#albumsWrapper article.item.full footer .meta .left")
    private List<WebElement> trackInfo;
    @FindBy(css="#albumsWrapper .fa.fa-list")
    private WebElement listViewButton;
    private static final String durationRe = "[^\\W•]+([1-9][0-99]+|[01]?[0-9]):([0-5]?[0-9])";
    private static final String songTotalRe = "^\\d{1,}|[^\\W•]";
    public AlbumsPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public boolean albumsArePresent() {
        return !albumTiles.isEmpty();
    }

    public boolean checkAlbumImage(WebElement element) {
        return element.getAttribute("style").contains("https://qa.koel.app/img/covers");
    }
    public void clickListView() {
        findElement(listViewButton).click();
    }
    public boolean checkAllAlbumCoverImage() {
        boolean check = true;
        int count = 0;
        while (check) {
            try {
                for (WebElement pic : albumCoverPics) {
                    wait.until(ExpectedConditions.visibilityOf(pic));
                    check = checkAlbumImage(pic);
                    count++;
                    if (count == albumCoverPics.size()) {
                        Reporter.log("Albums counted with cover images: " + count, true);
                        return check;
                    }
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public boolean checkAllAlbumText(List<WebElement> list) {
        boolean check = true;
        int count = 0;
        while (check) {
            try {
                for (WebElement title : list) {
                    wait.until(ExpectedConditions.visibilityOf(title));
                    check = !getTrackInfo(title).isEmpty();
                    count++;
                    if (count == list.size()) {
                        Reporter.log("Albums counted displaying correct text: " + count, true);
                        return check;
                    }
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return check;
    }
    public boolean checkHoveredElements(List<WebElement> hoveredElement) {
        boolean check = true;
        int limit = albumTiles.size();
        Reporter.log("limit: " + limit, true);
        for(int i = 0; i < limit; i++) {
                actions.moveToElement(footerInfoContainer.get(i)).perform();
                actions.moveToElement(hoveredElement.get(i)).perform();
                check = hoveredElement.get(i).isDisplayed();
                if(!check) {
                    return false;
                }
        }
        return check;
    }

    public boolean checkAlbumTitles() {
        return checkAllAlbumText(albumTitles);
    }
    public boolean checkAlbumArtists() {
        return checkAllAlbumText(albumArtists);
    }
    public boolean checkShuffleButtons() {
       return checkHoveredElements(shuffleBtn);
    }
    public boolean checkDownloadButtons() {
        return checkHoveredElements(downloadBtn);
    }
    public String getTrackInfo(WebElement element) {
        Reporter.log("Album track info: " + findElement(element).getText(), true);
        return findElement(element).getText();
    }

    public String getAlbumSongTotal(String trackInfo) {
        Pattern pattern = Pattern.compile(songTotalRe);
        Matcher matcher = pattern.matcher(trackInfo);
        if (matcher.find()) {
            Reporter.log("Song total: " + matcher.group(), true);
            return matcher.group();
        } else {
            Reporter.log("Did not find song total", true);
            return null;
        }
    }
    public String getAlbumDuration(String trackInfo) {
        Pattern pattern = Pattern.compile(durationRe);
        Matcher matcher = pattern.matcher(trackInfo);
        if (matcher.find()) {
            Reporter.log("Total duration: " + matcher.group(), true);
            return matcher.group();
        } else {
            Reporter.log("Did not find album duration", true);
            return null;
        }
    }
    public boolean albumDuration() {
        boolean check = true;
        int count = 0;
        while (check) {
            try{
                for (WebElement l: trackInfo) {
                    wait.until(ExpectedConditions.visibilityOf(l));
                    String track = getTrackInfo(l);
                    check = !getAlbumDuration(track).isEmpty();
                    count ++;
                    if (count == trackInfo.size()){
                        return check;
                    }
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return check;
    }
    public boolean albumSongTrackTotal() {
        boolean check = true;
        int count = 0;
        while (check) {
            try{
                for (WebElement l: trackInfo) {
                wait.until(ExpectedConditions.visibilityOf(l));
                String track = getTrackInfo(l);
                check = Integer.parseInt(getAlbumSongTotal(track)) > 0;
                count ++;
                    if (count == trackInfo.size()){
                    return check;
                    }
                 }
            } catch (TimeoutException e) {
               e.printStackTrace();
            }
        }
        return check;
    }
}
