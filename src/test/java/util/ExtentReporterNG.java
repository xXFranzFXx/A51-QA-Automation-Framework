package util;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;
import org.testng.xml.XmlSuite;


public class ExtentReporterNG {

    static ExtentReports extent;
    public static ExtentReports getReportObject() {

        String path = System.getProperty("user.dir") + "//reports//index.html";
        extent = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Koel Test Automation");
        reporter.config().setDocumentTitle("Test Results");
        extent.attachReporter(reporter);
        extent.setSystemInfo("QA Test Automation Engineer", "Franz Fernando");

        return extent;


}

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}