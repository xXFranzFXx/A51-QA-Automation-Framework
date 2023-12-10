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
    public static void setupBrowser() {
        threadDriver.set(initializeDriver());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        getDriver().get(url);
//        System.out.println(localStorage);

    }

    public static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
//        webStorage = (WebStorage) new Augmenter().augment(driver);
//        localStorage = webStorage.getLocalStorage();
        return driver;
    }

    public static void closeBrowser(){
        driver.quit();
    }

}
