package util.listeners;

import base.BaseTest;
import com.aventstack.extentreports.*;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.extentReports.ExtentManager;
import util.extentReports.ExtentTestManager;
import util.extentReports.TestUtil;
import util.logs.Log;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TestListener extends BaseTest implements ITestListener {
    //Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Extent Reports for Koel Automation Test Suite started!");
    }
    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Extent Reports for Koel Automation Test Suite ending!"));
        extent.flush();
    }
    @Override
    public synchronized void onTestStart(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " started!"));
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
    }
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        try {
            String curDir = System.getProperty("user.dir");
            TestUtil.takeScreenshotAtEndOfTest(result.getMethod().getMethodName());
//            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
            MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath( "/reports/extent-reports/screenshots/", result.getMethod().getMethodName() + ".png").build();
            test.get().log(Status.FAIL, "fail").addScreenCaptureFromPath("/reports/extent-reports/screenshots/" + result.getMethod().getMethodName() + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
            test.get().fail(result.getThrowable());
    }
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable());
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }
}