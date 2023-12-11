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
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.Augmenter;

public class BaseDefinitions {
    public static WebDriver driver;
    public WebDriverWait wait;
    private static final ThreadLocal <WebDriver> threadDriver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return threadDriver.get();
    }
    public static WebStorage webStorage;

    int timeSeconds = 10;
    public static String url = "https://qa.koel.app";
    public static String profileUrl = url + "/#!/profile";
    public static LocalStorage localStorage;
    public static void setupBrowser() throws MalformedURLException {
        threadDriver.set(initializeDriver(System.getProperty("browser")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
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
//                edgeOptions.addArguments(new String[]{"--remote-allow-origins=*", "--disable-notifications", "--start-maximized"});
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
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*", "--disable-notifications", "--start-maximized", "--incognito");
                return driver = new ChromeDriver(options);
        }
    }

    public static void closeBrowser(){
        driver.quit();
    }

}
