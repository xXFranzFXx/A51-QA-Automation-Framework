import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;
public class BaseTest {

    //constant variables used by helper methods
    public WebDriver driver;
    public  WebDriverWait wait;
    public Actions action;
    public String url;

    //stores an instance of WebDriver for each thread during tests execution
    private final ThreadLocal <WebDriver> threadDriver = new ThreadLocal<>();

    //return the current instance of WebDriver associated with the current thread.
    public WebDriver getDriver() {
        return threadDriver.get();
    }


    @BeforeMethod
    //use parameter for baseURL  from TestNG config file
    @Parameters({"baseURL"})
    public void setupBrowser(String baseURL) throws MalformedURLException {
        threadDriver.set(pickBrowser(System.getProperty("browser")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        navigateToLogin(baseURL);
    }

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
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
            case "cloud":
                return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(new String[]{"--remote-allow-origins=*", "--disable-notifications", "--start-maximized"});
                return driver = new ChromeDriver(options);
        }

    }

    public WebDriver lambdaTest() throws MalformedURLException {
        String username = "linkstasite.cs5";
        String authKey = "5DmWRPa0tmr9lZ0UlXDXRVGbqHEClVdDGnSsHrEMvx3jskb5Cu";
        String hub = "@hub.lambdatest.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "windows 10");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "120.0");
        caps.setCapability("resolution", "1024X768");
        caps.setCapability("build", "TestNG with Java");
        caps.setCapability("name", BaseTest.class.getName()); //or this.getClass().getName()
        caps.setCapability("plugin", "java-testNG");
        return new RemoteWebDriver(new URL("https://" + username + ":" + authKey + hub), caps);
    }
    //close the browser after successful test
    @AfterMethod
    public void closeBrowser() {
        //close the current instance and remove it from grid testing
        threadDriver.get().close();
        threadDriver.remove();
    }





    //navigates to login page
    public void navigateToLogin(String baseURL) {
        getDriver().get(baseURL);
    }
    //locates email input field and enters email address provided


    // Data providers start
    @DataProvider(name="LoginData")
    public Object[][] getDataFromDataProvider(){
        return new Object[][]{
                {"demo@class.com", "te$t$tudent"},
                {"invalidemail@class.com", "te$t$tudent"},
                {"demo@class.com", "InvalidPassword"},
                {"",""}
        };
    }

    @DataProvider(name="excel-data")
    public Object[][] excelDP() throws IOException {
        Object [][] arrObj;
        arrObj = getExcelData("./src/test/resources/assets/test.xlsx", "Sheet1");
        return arrObj;
    }
    // Data providers end

    //helper method for fetching data from excel sheet
    public String [][] getExcelData(String fileName, String sheetName) {
        String [][] data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);

            int numOfRows = sheet.getPhysicalNumberOfRows();
            int numOfColumns = row.getLastCellNum();

            XSSFCell cell;

            data = new String[numOfRows-1][numOfColumns];
            for(int i = 1; i < numOfRows; i++) {
                for(int j = 0; j < numOfColumns; j++) {
                    row = sheet.getRow(i);
                    cell = row.getCell(j);
                    data  [i-1][j] =  cell.getStringCellValue();
                }
            }

        } catch (Exception e) {
            System.out.println("Something went terribly wrong." + e);
        }
        return data;
    }

}