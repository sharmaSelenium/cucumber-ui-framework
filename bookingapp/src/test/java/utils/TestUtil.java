package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.cucumber.datatable.DataTable;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class TestUtil {

    /*
     * return key value
     * Datatable
     * */
    public static String getKeyValueFromDataTable(DataTable table, String key) {
        List<Map<String, String>> data = table.asMaps(String.class, String.class);
        for (Map<String, String> map : data)
            return map.get(key);
        return "Key not found";
    }

    public static String getAppDataYml(String appDataKey) {
        String appData = null;
        try {
            YAMLFactory factory = new YAMLFactory();
            ObjectMapper mapper = new ObjectMapper(factory);
            YmlPojoApplicationData ymlPojoApplicationData = mapper.readValue(new File(Constants.APP_DATA_PATH), YmlPojoApplicationData.class);
            appData = ymlPojoApplicationData.getAppdata().get(appDataKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appData;
    }

    public static String getFutureDateInFormat(String inputFormat , int days){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(inputFormat));
    }
}
