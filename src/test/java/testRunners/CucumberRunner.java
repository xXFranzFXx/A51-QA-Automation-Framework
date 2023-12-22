package testRunners;

import base.BaseDefinitions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

@CucumberOptions (
            features = {"src/test/resources/features"},
            glue= "stepDefinitions",
            publish = true,
            tags = "@homePage and not @ignore",
            monochrome = true,
            plugin = {
                    "pretty",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                },
            dryRun = true
    )
    public class CucumberRunner extends AbstractTestNGCucumberTests {
        private TestNGCucumberRunner testNGCucumberRunner;

        @BeforeClass(alwaysRun = true)
        public void setupCucumber(){
            testNGCucumberRunner =  new TestNGCucumberRunner(this.getClass());
        }

        @DataProvider
        public Object[][] features() {
            return testNGCucumberRunner.provideScenarios();
        }

//        allow parallel testing
//        @Override
//        @DataProvider(parallel = true)
//        public Object [][] scenarios() {
//            return super.scenarios();
//        }
        @AfterClass(alwaysRun = true)
        public void tearDownThisClass() {
            testNGCucumberRunner.finish();
        }
}
