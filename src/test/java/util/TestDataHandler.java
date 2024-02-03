package util;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TestDataHandler {
        Map<String, Object> testDataInMap = new HashMap<String, Object>();

        public Map<String, Object> getTestDataInMap() {
            return testDataInMap;
        }

        public void setTestDataInMap(String key, Object value) {
            this.testDataInMap = testDataInMap;
        }
        Map<String, ResultSet> dbDataInMap = new HashMap<String, ResultSet>();

    public Map<String, ResultSet> getdbDataInMap() {
        return dbDataInMap;
    }

    public void setDbDataInMap(Map<String, ResultSet> dbDataInMap) {
        this.dbDataInMap = dbDataInMap;
    }
    }


