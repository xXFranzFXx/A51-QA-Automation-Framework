package util;

import base.BaseTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.eo.Se;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class WebEventListener implements WebDriverListener, ITestListener{
     ExtentTest test;
     ExtentReports extent = ExtentReporterNG.getReportObject();
     WebDriver driver;
     ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;

    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        extentTest.get().fail(result.getThrowable());
        String testMethodName = result.getMethod().getMethodName();
        try {
           driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {

        }
        try {
            extentTest.get().addScreenCaptureFromPath(getScreenShotPath(testMethodName, driver),
                    result.getMethod().getMethodName());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extent.flush();
    }

    public void beforeAnyCall(Object target, Method method, Object[] args) {

            Reporter.log("Before calling method: " + method.getName());
        }

        public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
            Reporter.log("After calling method: " + method.getName());
        }

        public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
           Reporter.log("Error while calling method: " + method.getName() + " - " + e.getMessage(), true);
        }

        public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
           Reporter.log("Before calling WebDriver method: " + method.getName());
        }

        public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
           Reporter.log("After calling WebDriver method: " + method.getName());
        }

        public void beforeGet(WebDriver driver, String url) {
           Reporter.log("Before navigating to URL: " + url);
        }

        public void afterGet(WebDriver driver, String url) {
           Reporter.log("After navigating to URL: " + url);
        }

        public void beforeGetCurrentUrl(WebDriver driver) {
           Reporter.log("Before getting current URL.");
        }

        public void afterGetCurrentUrl(String result, WebDriver driver) {
           Reporter.log("After getting current URL: " + result);
        }

        public void beforeGetTitle(WebDriver driver) {
           Reporter.log("Before getting page title.");
        }

        public void afterGetTitle(WebDriver driver, String result) {
           Reporter.log("After getting page title: " + result);
        }

        public void beforeFindElement(WebDriver driver, By locator) {
           Reporter.log("Before finding element by: " + locator);
        }

        public void afterFindElement(WebDriver driver, By locator, WebElement result) {
           Reporter.log("After finding element by: " + locator);
        }

        public void beforeFindElements(WebDriver driver, By locator) {
           Reporter.log("Before finding elements by: " + locator);
        }

        public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
           Reporter.log("After finding elements by: " + locator);
        }

        public void beforeGetPageSource(WebDriver driver) {
           Reporter.log("Before getting page source.");
        }

        public void afterGetPageSource(WebDriver driver, String result) {
           Reporter.log("After getting page source.");
        }

        public void beforeClose(WebDriver driver) {
           Reporter.log("Before closing the WebDriver.");
        }

        public void afterClose(WebDriver driver) {
           Reporter.log("After closing the WebDriver.");
        }

        public void beforeQuit(WebDriver driver) {
           Reporter.log("Before quitting the WebDriver.");
        }

        public void afterQuit(WebDriver driver) {
           Reporter.log("After quitting the WebDriver.");
        }

        public void beforeGetWindowHandles(WebDriver driver) {
           Reporter.log("Before getting window handles.");
        }

        public void afterGetWindowHandles(WebDriver driver, Set<String> result) {
           Reporter.log("After getting window handles.");
        }

        public void beforeGetWindowHandle(WebDriver driver) {
           Reporter.log("Before getting window handle.");
        }

        public void afterGetWindowHandle(WebDriver driver, String result) {
           Reporter.log("After getting window handle.");
        }

        public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
           Reporter.log("Before executing script: " + script);
        }

        public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
           Reporter.log("After executing script: " + script);
        }

        public void beforeExecuteAsyncScript(WebDriver driver, String script, Object[] args) {
           Reporter.log("Before executing async script: " + script);
        }

        public void afterExecuteAsyncScript(WebDriver driver, String script, Object[] args, Object result) {
           Reporter.log("After executing async script: " + script);
        }

        public void beforePerform(WebDriver driver, Collection<Sequence> actions) {
           Reporter.log("Before performing actions.");
        }

        public void afterPerform(WebDriver driver, Collection<Sequence> actions) {
           Reporter.log("After performing actions.");
        }

        public void beforeResetInputState(WebDriver driver) {
           Reporter.log("Before resetting input state.");
        }

        public void afterResetInputState(WebDriver driver) {
           Reporter.log("After resetting input state.");
        }

        public void beforeAnyWebElementCall(WebElement element, Method method, Object[] args) {
           Reporter.log("Before calling WebElement method: " + method.getName());
        }

        public void afterAnyWebElementCall(WebElement element, Method method, Object[] args, Object result) {
           Reporter.log("After calling WebElement method: " + method.getName());
        }

        public void beforeClick(WebElement element) {
           Reporter.log("Before clicking on element.");
        }

        public void afterClick(WebElement element) {
           Reporter.log("After clicking on element.");
        }

        public void beforeSubmit(WebElement element) {
           Reporter.log("Before submitting a form element.");
        }

        public void afterSubmit(WebElement element) {
           Reporter.log("After submitting a form element.");
        }

        public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
           Reporter.log("Before sending keys to element.");
        }

        public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
           Reporter.log("After sending keys to element.");
        }

        public void beforeClear(WebElement element) {
           Reporter.log("Before clearing the text of an element.");
        }

        public void afterClear(WebElement element) {
           Reporter.log("After clearing the text of an element.");
        }

        public void beforeGetTagName(WebElement element) {
           Reporter.log("Before getting the tag name of an element.");
        }

        public void afterGetTagName(WebElement element, String result) {
           Reporter.log("After getting the tag name of an element: " + result);
        }

        public void beforeGetAttribute(WebElement element, String name) {
           Reporter.log("Before getting an attribute of an element: " + name);
        }

        public void afterGetAttribute(WebElement element, String name, String result) {
           Reporter.log("After getting an attribute of an element: " + name);
        }

        public void beforeIsSelected(WebElement element) {
           Reporter.log("Before checking if element is selected.");
        }

        public void afterIsSelected(WebElement element, boolean result) {
           Reporter.log("After checking if element is selected: " + result);
        }

        public void beforeIsEnabled(WebElement element) {
           Reporter.log("Before checking if element is enabled.");
        }

        public void afterIsEnabled(WebElement element, boolean result) {
           Reporter.log("After checking if element is enabled: " + result);
        }

        public void beforeGetText(WebElement element) {
           Reporter.log("Before getting text from element.");
        }

        public void afterGetText(WebElement element, String result) {
           Reporter.log("After getting text from element: " + result);
        }

        public void beforeFindElement(WebElement element, By locator) {
           Reporter.log("Before finding element within element: " + locator);
        }

        public void afterFindElement(WebElement element, By locator, WebElement result) {
           Reporter.log("After finding element within element: " + locator);
        }

        public void beforeFindElements(WebElement element, By locator) {
           Reporter.log("Before finding elements within element: " + locator);
        }

        public void afterFindElements(WebElement element, By locator, List<WebElement> result) {
           Reporter.log("After finding elements within element: " + locator);
        }
    }