package util;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class TestUtil extends BaseTest {
    public static long sysTime = System.currentTimeMillis();;
    public static void takeScreenshotAtEndOfTest(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir +"/reports/extent-reports/screenshots/" + fileName + ".png"));
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
    public static  String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        return df.format(new Date());
    }
   public static String convertSecondToHHMMSSString(int nSecondTime) {
        return LocalTime.MIN.plusSeconds(nSecondTime).toString();
    }
    public static  Map<String, Map<String, LinkedHashMap<String, String>>>createProcessedResultSetMap(Map<String, ResultSet> dataMap) throws SQLException {
        Map<String, Map<String, LinkedHashMap<String, String>>> multiRsMap = new LinkedHashMap<>();
        Set<String> s = dataMap.keySet();
        for (String str: s) {
            multiRsMap.put(str, processResultSet(str, dataMap.get(str)));
        }
        return multiRsMap;
    }
    public static Map<String, LinkedHashMap<String, String>> processResultSet(String name, ResultSet rs){
        ArrayList<String> columnNames = new ArrayList<String>();
        LinkedHashMap<String, String> rowDetails = new LinkedHashMap<String, String>();
        Map<String, LinkedHashMap<String, String>> resultMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        ResultSetMetaData rsm = null;
        if (rs != null)
        {
            try
            {
                rsm = rs.getMetaData();
                for (int i = 1; i <= rsm.getColumnCount(); i++)
                {
                    System.out.println(i + " -> " + rsm.getColumnName(i));
                    columnNames.add(rsm.getColumnName(i));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            int rowCount = 1;
            while (rs.next())
            {
                for (int i = 1; i <= rsm.getColumnCount(); i++)
                {
                    rowDetails.put(rsm.getColumnName(i), rs.getString(i));
                }
                resultMap.put(Integer.valueOf(rowCount).toString(), rowDetails);
//            multiRsMap.put(name, resultMap);
                rowCount++;
                rowDetails = new LinkedHashMap<>();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return resultMap;
    }

}