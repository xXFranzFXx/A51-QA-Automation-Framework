import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegistrationPage;

import java.net.MalformedURLException;
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
    private String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    LoginPage loginPage;
    HomePage homePage;
    ProfilePage profilePage;

    public LogoutTests() {
        super();
    }
    @BeforeMethod
    @Parameters({"baseURL"})
    public void setup(String baseURL) throws MalformedURLException {
        setupBrowser(baseURL);
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        profilePage = new ProfilePage(getDriver());
    }


    @Test(description = "Log in and verify visibility of logout button, then log out")
    public void useLogoutButton() {

        loginPage.loginValidCredentials();
        Assert.assertTrue(homePage.checkForLogoutBtn());

        homePage.clickLogoutButton();
        Assert.assertTrue(loginPage.getRegistrationLink());
        Reporter.log("Successfully logged out after logging in", true);

    }


    @Test(dependsOnMethods = { "useLogoutButton" }, description = "Update username and password then logout and verify navigation back to login screen")
    public void logoutAfterProfileUpdate() {


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
    @AfterMethod
    public void tearDown() {
        closeBrowser();
    }


}
