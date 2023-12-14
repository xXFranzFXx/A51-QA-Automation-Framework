package stepDefinitions;

import com.beust.jcommander.Parameter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import pages.ProfilePage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.Augmenter;

public class BaseDefinitions {
    public static WebDriver driver;
    private static final ThreadLocal <WebDriver> threadDriver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return threadDriver.get();
    }
//    public static WebStorage webStorage;

    static int timeSeconds = 10;
    public static String url = "https://qa.koel.app";
    public static String pageUrl = url + "/#!/";
    public static String homeUrl = buildUrl("home");
    public static String profileUrl = buildUrl("profile");
    public static String currentQueueUrl = buildUrl("queue");
    public static String  allSongsUrl = buildUrl("songs");
    public static String albumsUrl = buildUrl("albums");
    public static String artistUrl = buildUrl("artists");
    public static String favoritesUrl = buildUrl("favorites");
    public static String recentlyPlayedUrl = buildUrl("recently-played");
    public static String buildUrl(String page){
        return (pageUrl + page);
    }
//    public static LocalStorage localStorage;
    public static void setupBrowser() throws MalformedURLException {
        threadDriver.set(initializeDriver(System.getProperty("browser", "")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeSeconds));
        getDriver().get(url);
//        System.out.println(localStorage);

    }
    public static WebDriver initializeDriver(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://192.168.0.15:4444";
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("-private");
                return driver = new FirefoxDriver(optionsFirefox);
            //gradle clean test -Dbrowser=MicrosoftEdge
            case "MicrosoftEdge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                return driver = new EdgeDriver();
            //gradle clean test -Dbrowser=grid-edge
            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            //gradle clean test -Dbrowser=grid-firefox
            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            //gradle clean test -Dbrowser=grid-chrome
            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            case "cloud":
                return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*", "--disable-notifications", "--start-maximized", "--incognito");
                return driver = new ChromeDriver(options);
        }
    }
    public static WebDriver lambdaTest() throws MalformedURLException {
        String username = "linkstasite.cs5";
        String authKey = "5DmWRPa0tmr9lZ0UlXDXRVGbqHEClVdDGnSsHrEMvx3jskb5Cu";
        String hub = "@hub.lambdatest.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "windows 10");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "120.0");
        caps.setCapability("resolution", "1024X768");
        caps.setCapability("build", "TestNG with Java");
        caps.setCapability("name", BaseDefinitions.class.getName()); //or this.getClass().getName()
        caps.setCapability("plugin", "java-testNG");
        return new RemoteWebDriver(new URL("https://" + username + ":" + authKey + hub), caps);
    }

    public static void closeBrowser(){
        driver.quit();
    }

}
