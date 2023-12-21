package util.listeners;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.WebDriverListener;
import util.logs.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TestListener  implements WebDriverListener {

    public void beforeAnyCall(Object target, Method method, Object[] args) {
        Log.info( "Before calling method: " + method.getName());
    }

    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        Log.info("After calling method: " + method.getName());
    }

    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        Log.fatal( "Error while calling method: " + method.getName() + " - " + e.getMessage());
    }

    public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
        Log.info("Before calling WebDriver method: " + method.getName());
    }

    public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
        Log.info("After calling WebDriver method: " + method.getName());
    }

    public void beforeGet(WebDriver driver, String url) {
        Log.info("Before navigating to URL: " + url);
    }

    public void afterGet(WebDriver driver, String url) {
        Log.info("After navigating to URL: " + url);
    }

    public void beforeGetCurrentUrl(WebDriver driver) {
        Log.info("Before getting current URL.");
    }

    public void afterGetCurrentUrl(String result, WebDriver driver) {
        Log.info("After getting current URL: " + result);
    }

    public void beforeGetTitle(WebDriver driver) {
        Log.info("Before getting page title.");
    }

    public void afterGetTitle(WebDriver driver, String result) {
        Log.info("After getting page title: " + result);
    }

    public void beforeFindElement(WebDriver driver, By locator) {
        Log.info("Before finding element by: " + locator);
    }

    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        Log.info("After finding element by: " + locator);
    }

    public void beforeFindElements(WebDriver driver, By locator) {
        Log.info("Before finding elements by: " + locator);
    }

    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        Log.info("After finding elements by: " + locator);
    }

    public void beforeGetPageSource(WebDriver driver) {
        Log.info("Before getting page source.");
    }

    public void afterGetPageSource(WebDriver driver, String result) {
        Log.info("After getting page source.");
    }

    public void beforeClose(WebDriver driver) {
        Log.info("Before closing the WebDriver.");
    }

    public void afterClose(WebDriver driver) {
        Log.info("After closing the WebDriver.");
    }

    public void beforeQuit(WebDriver driver) {
        Log.info("Before quitting the WebDriver.");
    }

    public void afterQuit(WebDriver driver) {
        Log.info("After quitting the WebDriver.");
    }

    public void beforeGetWindowHandles(WebDriver driver) {
        Log.info("Before getting window handles.");
    }

    public void afterGetWindowHandles(WebDriver driver, Set<String> result) {
        Log.info("After getting window handles.");
    }

    public void beforeGetWindowHandle(WebDriver driver) {
        Log.info("Before getting window handle.");
    }

    public void afterGetWindowHandle(WebDriver driver, String result) {
        Log.info("After getting window handle.");
    }

    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
        Log.info("Before executing script: " + script);
    }

    public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
        Log.info("After executing script: " + script);
    }

    public void beforeExecuteAsyncScript(WebDriver driver, String script, Object[] args) {
        Log.info("Before executing async script: " + script);
    }

    public void afterExecuteAsyncScript(WebDriver driver, String script, Object[] args, Object result) {
        Log.info("After executing async script: " + script);
    }

    public void beforePerform(WebDriver driver, Collection<Sequence> actions) {
        Log.info("Before performing actions.");
    }

    public void afterPerform(WebDriver driver, Collection<Sequence> actions) {
        Log.info("After performing actions.");
    }

    public void beforeResetInputState(WebDriver driver) {
        Log.info("Before resetting input state.");
    }

    public void afterResetInputState(WebDriver driver) {
        Log.info("After resetting input state.");
    }

    public void beforeAnyWebElementCall(WebElement element, Method method, Object[] args) {
        Log.info("Before calling WebElement method: " + method.getName());
    }

    public void afterAnyWebElementCall(WebElement element, Method method, Object[] args, Object result) {
        Log.info("After calling WebElement method: " + method.getName());
    }

    public void beforeClick(WebElement element) {
        Log.info("Before clicking on element.");
    }

    public void afterClick(WebElement element) {
        Log.info("After clicking on element.");
    }

    public void beforeSubmit(WebElement element) {
        Log.info("Before submitting a form element.");
    }

    public void afterSubmit(WebElement element) {
        Log.info("After submitting a form element.");
    }

    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        Log.info("Before sending keys to element.");
    }

    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        Log.info("After sending keys to element.");
    }

    public void beforeClear(WebElement element) {
        Log.info("Before clearing the text of an element.");
    }

    public void afterClear(WebElement element) {
        Log.info("After clearing the text of an element.");
    }

    public void beforeGetTagName(WebElement element) {
        Log.info("Before getting the tag name of an element.");
    }

    public void afterGetTagName(WebElement element, String result) {
        Log.info("After getting the tag name of an element: " + result);
    }

    public void beforeGetAttribute(WebElement element, String name) {
        Log.info("Before getting an attribute of an element: " + name);
    }

    public void afterGetAttribute(WebElement element, String name, String result) {
        Log.info("After getting an attribute of an element: " + name);
    }

    public void beforeIsSelected(WebElement element) {
        Log.info("Before checking if element is selected.");
    }

    public void afterIsSelected(WebElement element, boolean result) {
        Log.info("After checking if element is selected: " + result);
    }

    public void beforeIsEnabled(WebElement element) {
        Log.info("Before checking if element is enabled.");
    }

    public void afterIsEnabled(WebElement element, boolean result) {
        Log.info("After checking if element is enabled: " + result);
    }

    public void beforeGetText(WebElement element) {
        Log.info("Before getting text from element.");
    }

    public void afterGetText(WebElement element, String result) {
        Log.info("After getting text from element: " + result);
    }

    public void beforeFindElement(WebElement element, By locator) {
        Log.info("Before finding element within element: " + locator);
    }

    public void afterFindElement(WebElement element, By locator, WebElement result) {
        Log.info("After finding element within element: " + locator);
    }

    public void beforeFindElements(WebElement element, By locator) {
        Log.info("Before finding elements within element: " + locator);
    }

    public void afterFindElements(WebElement element, By locator, List<WebElement> result) {
        Log.info("After finding elements within element: " + locator);
    }
}