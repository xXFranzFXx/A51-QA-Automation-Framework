package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

import java.net.MalformedURLException;
import java.util.UUID;


public class LogoutStepDefinitions extends  BaseDefinitions{
    @Before
    public void setup() {
        setupBrowser();
    }
    @After
    public void close() {
        closeBrowser();
    }
    @Given("User opens Log in page")
    public void userOpensWebpage() throws MalformedURLException {
       getDriver().get("https://qa.koel.app");
    }


    @Given("User is logged in")
    public void login() {
            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.loginValidCredentials();
        }
    @And("Logout button is visible next to profile pic")
    public void logoutBtnVisible() {
        HomePage homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.checkForLogoutBtn());
    }


    @When("User clicks logout button")
    public void userClicksLogoutButton() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.clickLogout();
    }

    @Then("User is logged out and navigates back to login screen")
    public void userIsLoggedOutAndNavigatesBackToLoginScreen() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

    @When("User clicks profile pic")
    public void userClicksProfilePic() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.clickAvatar();
    }

    @Then("Profile page is opened")
    public void profilePageIsOpened() {
        String url = "https://qa.koel.app/#!/profile";
        Assert.assertEquals(getDriver().getCurrentUrl(), url);
    }

    @When("User enters current password")
    public void userEntersCurrentPassword() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.provideCurrentPassword("te$t$tudent1");
    }

    @And("User enters updated name")
    public void userEntersUpdatedName() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.provideRandomProfileName(newName);
    }
    private static String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    private static final String newName = generateRandomName();

    @And("User clicks save button")
    public void userClicksSaveButton() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.clickSave();

    }

    @Then("Success message is displayed")
    public void successMessageIsDisplayed() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        Assert.assertTrue(profilePage.notificationPopup());
    }

    @When("Success message disappears")
    public void successMessageDisappears() {
        ProfilePage profilePage = new ProfilePage(getDriver());
        Assert.assertTrue(profilePage.notificationHasDisappeared());
    }

    @Then("User navigates back to login screen")
    public void userNavigatesBackToLoginScreen() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.getRegistrationLink());
    }

}
