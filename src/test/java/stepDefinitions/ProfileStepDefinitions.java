package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

public class ProfileStepDefinitions extends BaseDefinitions{
    static ProfilePage profilePage;

    @When("User clicks on a theme {string}")
    public void userClicksOnATheme(String theme) {
        profilePage = new ProfilePage(getDriver());
        profilePage.clickTheme(theme);
    }
    @Then("The profile theme will be updated to {string}")
    public void theProfileThemeWillBeUpdatedTo(String theme) {
        profilePage = new ProfilePage(getDriver());
        Assert.assertTrue(profilePage.verifyTheme(theme));
    }

    @And("User navigates to profile page")
    public void userNavigatesToProfilePage() {
        profilePage = new ProfilePage(getDriver());
        profilePage.clickAvatar();
    }
}
