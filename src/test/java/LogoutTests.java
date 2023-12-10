import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

import java.util.UUID;

/**
 * Story:
 * As a user, I want to be able to log out of my account
 * Acceptance Criteria:
 * 1. 'Log student out' button should be present on the homepage, next to 'Profile' button
 * 2. User should be able to log out after successful login
 * 3. User should be navigated to the Login page after logging out
 * 4. User should be able to log out after updating email and password
 */
public class LogoutTests extends BaseTest {


    @Test(description = "Log in and verify visibility of logout button, then log out")
    public void useLogoutButton() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.checkForLogoutBtn());

        homePage.clickLogoutButton();
        Assert.assertTrue(loginPage.getRegistrationLink());
        Reporter.log("Successfully logged out after logging in", true);

    }


    @Test(dependsOnMethods = { "useLogoutButton" }, description = "Update username and password then logout and verify navigation back to login screen")
    public void logoutAfterProfileUpdate() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        ProfilePage profilePage = new ProfilePage(getDriver());
        loginPage.loginValidCredentials();
        String randomNm = generateRandomName();
        String password = "te$t$tudent1";
        String profileName = profilePage.getProfileName();
        profilePage.clickAvatar()
                .provideNewPassword("te$t$tudent1")
                .provideRandomProfileName(randomNm)
                .provideCurrentPassword(password);

        Assert.assertTrue(profilePage.clickSave());
        profilePage.clickLogout();
        Assert.assertTrue(loginPage.getRegistrationLink());
        Reporter.log("User has logged out after updating username and password and redirected to login page", true);
    }
    private String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
