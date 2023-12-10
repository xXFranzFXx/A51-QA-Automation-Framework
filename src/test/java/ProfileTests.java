import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

import java.util.UUID;

public class ProfileTests extends BaseTest {


    @Test(description = "Update profile name")
    public void changeProfileName()  throws InterruptedException {
        ProfilePage profilePage = new ProfilePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        String randomNm = generateRandomName();
        String profileName = profilePage.getProfileName();

        loginPage.loginValidCredentials();
        profilePage.clickAvatar()
            .provideCurrentPassword("te$t$tudent")
            .provideRandomProfileName(randomNm)
            .clickSave();
        Assert.assertEquals(profileName, randomNm);

    }
    private String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Test(description = "Update theme to In the Pines")
    public void choosePinesTheme() {
        HomePage homePage = new HomePage(getDriver());
        ProfilePage profilePage = new ProfilePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginValidCredentials();
        profilePage.clickAvatar()
                .pinesTheme();
        Assert.assertTrue(homePage.checkTheme("pines"));
        Reporter.log("changed theme to pines",   true);
    }
}
