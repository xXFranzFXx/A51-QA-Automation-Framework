package util;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestUtil extends BaseTest{
    public static long sysTime = System.currentTimeMillis();;
    public static void takeScreenshotAtEndOfTest(String fileName) throws IOException {
        File scrFile;
        scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir +"/reports/extent-reports/screenshots/" + fileName + ".png"));
    }
    public static  String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        return df.format(new Date());
    }

}