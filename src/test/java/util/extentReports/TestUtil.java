package util.extentReports;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TestUtil extends BaseTest {
    public static long sysTime;
    public static void takeScreenshotAtEndOfTest(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        sysTime = System.currentTimeMillis();
        FileUtils.copyFile(scrFile, new File(currentDir +"/reports/extent-reports/screenshots/" + fileName + ".png"));
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}