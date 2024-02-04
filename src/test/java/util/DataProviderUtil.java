package util;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class DataProviderUtil {

    @DataProvider(name="PlaylistData")
    public Object[][] getSearchData(Method method){
        if(method.getName().equalsIgnoreCase("createOneCharacterPlaylistName")) {
        return new Object[][]{
                {"a"}
        };
        } else {
            return new Object[][] {
                    {"playlist"}
            };
        }
    }
}
