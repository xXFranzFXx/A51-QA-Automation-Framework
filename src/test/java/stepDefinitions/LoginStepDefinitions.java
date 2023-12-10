package stepDefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import pages.ProfilePage;

import java.net.MalformedURLException;



public class LoginStepDefinitions  extends BaseDefinitions{
    @Before
    public void setup() {
        setupBrowser();
    }
    @After
    public void close() {
        closeBrowser();
    }




    @Then("User should navigate to home page")
    public void checkHomePage() {
    }

    @When("User enters email {string}")
    public void userEntersEmail(String email) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterEmail(email);
    }

    @And("User enters password {string}")
    public void userEntersPassword(String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.providePassword(password);
    }

    @And("User clicks submit")
    public void userClicksSubmit() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickSubmitBtn();
    }

    @Then("User should still be on Login page")
    public void userShouldStillBeOnLoginPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @Given("User logs in")
    public void userLogsIn() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
    }

    @When("User provides current password {string}")
    public void userProvidesCurrentPassword(String password) {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.provideCurrentPassword(password);
    }

    @And("User provides new email address {string}")
    public void userProvidesNewEmailAddress(String newEmail) {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.provideNewEmail(newEmail);
    }

    @And("User provides new password {string}")
    public void userProvidesNewPassword(String newPasswd) {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.provideNewPassword(newPasswd);
    }

    @Given("User is on login page")
    public void userIsOnLoginPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }
}
