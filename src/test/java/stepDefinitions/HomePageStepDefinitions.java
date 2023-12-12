package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;

public class HomePageStepDefinitions extends BaseDefinitions {
    static LoginPage loginPage;
    static HomePage homePage;
    @Given("User logs in as new user")
    public void loginAsNewUser() {
        loginPage = new LoginPage(getDriver());
        loginPage.loginAsNewUser();
    }


    @Given("User is on homepage")
    public void homePage() {
        getDriver().get(homeUrl);
    }


    @Then("User should not see recently played songs")
    public void userShouldNotSeeRecentlyPlayedSongs() {
        homePage = new HomePage(getDriver());
        Assert.assertFalse(homePage.noRecentlyPlayed());
    }

    @When("User plays a song")
    public void userPlaysASong() {

    }

    @Then("User should see recently played songs")
    public void userShouldSeeRecentlyPlayedSongs() {

    }

    @Then("welcome message should contain user's name")
    public void welcomeMessageShouldContainUserSName() {
    }

    @Then("User should see welcome message {string}")
    public void userShouldSeeWelcomeMessage(String welcomeMsg) {
        homePage = new HomePage(getDriver());
        Assert.assertEquals(homePage.getWelcomMsg(), welcomeMsg);
    }
}
