package stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.HomePage;
import resources.BaseTest;

public class TestLogin extends BaseTest {
 @When("I enter email")
 public void enterEmail() {
  LoginPage loginPage = new LoginPage(getDriver());
  loginPage.provideEmail("fake@fakeaccount.com");
 }
}
