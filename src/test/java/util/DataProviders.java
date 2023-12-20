package util;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders extends ExcelFile {
    @DataProvider(name="LoginData", parallel=true)
    public Object[][] getDataFromDataProvider(){
        return new Object[][]{
                {"demo@class.com", "te$t$tudent"},
                {"invalidemail@class.com", "te$t$tudent"},
                {"demo@class.com", "InvalidPassword"},
                {"",""}
        };
    }
    @DataProvider(name="excel-data", parallel=true)
    public Object[][] excelDP() throws IOException {
        Object [][] arrObj;
        arrObj = getExcelData("test.xlsx", "Sheet1");
        return arrObj;
    }
}
