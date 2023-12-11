import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ProfileTests extends BaseTest {

    @Test(description = "Update profile name")
    public void changeProfileName()  throws InterruptedException {
        ProfilePage profilePage = new ProfilePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        String randomNm = generateRandomName();
        String profileName = profilePage.getProfileName();
        try {
            loginPage.loginValidCredentials();
            profilePage.clickAvatar()
                    .provideCurrentPassword("te$t$tudent1")
                    .provideRandomProfileName(randomNm)
                    .clickSave();
            Assert.assertEquals(profileName, randomNm);
            Reporter.log("Updated user name to" + randomNm, true);
        }catch (Exception e) {
            Reporter.log("Failed to update user name" + e, true);
        }
    }
    private String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Test(description = "Update theme to In the Pines")
    public void choosePinesTheme() {
        HomePage homePage = new HomePage(getDriver());
        ProfilePage profilePage = new ProfilePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        try {
            loginPage.loginValidCredentials();
            profilePage.clickAvatar()
                    .clickTheme("pines");
            Assert.assertTrue(profilePage.verifyTheme("pines"));
            Reporter.log("Changed theme to pines", true);
        } catch(Exception e) {
            Reporter.log("Failed to change theme to pines theme" + e, true);
            Assert.assertFalse(false);
        }
    }
}
