import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
public class Homework16 extends BaseTest {
    @Test
    public void registrationNavigation() {
        //setup Chrome browser options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //instruct Chrome browser to implement implicit wait to load DOM
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //navigate to koel app url
        String url = "https://qa.koel.app/";
        driver.get(url);
        //locate registration link by css selector and click on it
        WebElement registrationLink = driver.findElement(By.cssSelector("[href='registration']"));
        registrationLink.click();
        //assertion to check that the link was clicked by comparing page url with expected url after clicking
        String registrationUrl = "https://qa.koel.app/registration";
        Assert.assertEquals(driver.getCurrentUrl(), registrationUrl);
        //close the browser after test completion
        driver.quit();
    }
}
