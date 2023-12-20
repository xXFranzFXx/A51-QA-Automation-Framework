package util.listeners;

import base.BaseDefinitions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.xml.XmlSuite;
import util.TestUtil;
import util.logs.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExtentReportListener extends AbstractTestNGCucumberTests implements IReporter {
    WebDriver driver = BaseDefinitions.getDriver();
    private static ExtentReports extent ;
    private static final String OUTPUT_FOLDER = "reports/extent-reports/";
    private static final String FILE_NAME = "cucumber-extent.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        init();
        for (ISuite suite : suites) {
          Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }
        extent.flush();
    }

    public void init() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Automation Test Results");
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setReportUsesManualConfiguration(true);

    }

    private void buildTestNodes(IResultMap tests, Status status) {
        ExtentTest test;
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.createTest(result.getMethod().getMethodName());
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                    Log.warn("Test Status: " + status.toString().toLowerCase() + result.getThrowable());
                } else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                    Log.info("Test Status: " + status.toString().toLowerCase());
                }
                if (result.getStatus() == ITestResult.FAILURE) {
                    try {
                        TestUtil.takeScreenshotAtEndOfTest(result.getMethod().getMethodName());
                        test.fail("Screenshot", TestUtil.getScreenShot(result.getMethod().getMethodName(), driver));
                        Log.fatal("Test Status: " + status.toString().toLowerCase());

                    } catch (Exception e) {
                        test.fail("Screenshot not available");
                    }
                }
                extent.flush();
            }
        }
    }
//    @After
//    public void afterScenario(Scenario scenario) {
//        if (scenario.isFailed()) {
//            try {
//                byte[] screenshot = ((TakesScreenshot) BaseDefinitions.getDriver()).getScreenshotAs(OutputType.BYTES);
//                scenario.attach(screenshot, "image/png", scenario.getName());
//                extent.fail("Test Failed");
//                extent.addScreenCaptureFromBase64String(new String(screenshot), scenario.getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            extentTest.pass("Test Passed");
//        }
//    }
}
