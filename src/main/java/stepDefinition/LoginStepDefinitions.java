package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;


public class LoginStepDefinitions {

    WebDriver driver;
    WebDriverWait wait;
    @Before
//    @Given("I open browser")
    public void openBrowser() {
        WebDriverManager.chromdedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10L));

    }
    @After
    public void closeBrowser() {}
    driver.quit();

    @And("I open Login Page")
    public void iOpenLoginPage() {
        driver.get("https://qa.koel.app/");
    }

    @When("I enter email {string}")
    public void iEnterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[type='email']"))).sendKeys(email));
    }
}
