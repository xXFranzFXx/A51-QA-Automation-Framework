package util;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import base.BaseDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TestUtil extends BaseDefinitions {
    public static long sysTime = System.currentTimeMillis();;
    public static void takeScreenshotAtEndOfTest(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir +"/reports/extent-reports/screenshots/" + fileName + ".png"));
    }
    public static Media getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = "/reports/extent-reports/screenshots/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return MediaEntityBuilder.createScreenCaptureFromPath(destinationFile).build();
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}