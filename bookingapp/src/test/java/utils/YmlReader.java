package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YmlReader {

    public static List<String> createInsertQueriesList(String insertCondition) {
        List<Object> ymlInsertList = readYamlAsList(insertCondition, "insert");
        return createInsertQueriesFromYamlList(ymlInsertList);
    }

    public static List<String> createDeleteQueriesList(String deleteCondition) {
        List<Object> ymlDeleteList = readYamlAsList(deleteCondition, "delete");
        return createDeleteQueriesFromYamlList(ymlDeleteList);
    }

    public static String getAppDataYml(String appDataKey) {
        String appData = null;
        try {
            YAMLFactory factory = new YAMLFactory();
            ObjectMapper mapper = new ObjectMapper(factory);
            YmlPojoApplicationData ymlPojoApplicationData = mapper.readValue(new File(Constants.YAML_APPDATA_FILE_PATH), YmlPojoApplicationData.class);
            appData = ymlPojoApplicationData.getAppdata().get(appDataKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appData;
    }

    private static List<Object> readYamlAsList(String fileName, String queryType) {
        List<Object> ymlInsertList = null;
        try {
            YAMLFactory factory = new YAMLFactory();
            ObjectMapper mapper = new ObjectMapper(factory);
            JsonParser parser;
            if (queryType.equalsIgnoreCase("insert")) {
                parser = factory.createParser(new File(Constants.YAML_INSERT_FILE_PATH + fileName + ".yaml"));
            } else {
                parser = factory.createParser(new File(Constants.YAML_DELETE_FILE_PATH + fileName + ".yaml"));
            }
            ymlInsertList = mapper.readValues(parser, new TypeReference<YmlPojoOracleData>() {
            }).readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ymlInsertList;
    }

    private static List<String> createInsertQueriesFromYamlList(List<Object> ymlInsertList) {
        List<String> insertQueriesList = new ArrayList<String>();
        for (Object insertObject : ymlInsertList) {
            YmlPojoOracleData ymlInsert = (YmlPojoOracleData) insertObject;
            String tableName = ymlInsert.getTable();
            HashMap<String, Object> columnMap = ymlInsert.getColumn();
            String columnQuery = "";
            String valueQuery = "";
            String colValue;
            for (Map.Entry<String, Object> columnData : columnMap.entrySet()) {
                columnQuery = columnQuery + ", " + columnData.getKey();
                if (columnData.getValue() != null) {
                    colValue = columnData.getValue().toString();
                    if (colValue.startsWith("$$")) {
                        colValue = "'" + colValue.replaceAll("\\$\\$", "") + "'";
                    }
                } else {
                    colValue = "null";
                }
                valueQuery = valueQuery + ", " + colValue;
            }
            columnQuery = columnQuery.replaceFirst(", ", "");
            valueQuery = valueQuery.replaceFirst(", ", "");
            String insertQuery = "insert into " + tableName + "(" + columnQuery + ") values (" + valueQuery + ")";
            insertQueriesList.add(insertQuery);
        }
        return insertQueriesList;
    }

    private static List<String> createDeleteQueriesFromYamlList(List<Object> ymlInsertList) {
        List<String> deleteQueriesList = new ArrayList<String>();
        for (Object insertObject : ymlInsertList) {
            YmlPojoOracleData ymlDelete = (YmlPojoOracleData) insertObject;
            String tableName = ymlDelete.getTable();
            HashMap<String, Object> columnMap = ymlDelete.getColumn();
            String whereClass = "";
            if (!(columnMap == null)) {
                for (Map.Entry<String, Object> columnData : columnMap.entrySet()) {
                    String column = "t." + columnData.getKey();
                    String value = columnData.getValue().toString();
                    whereClass = whereClass + " and " + column + " In " + value;
                }
                whereClass = whereClass.replaceFirst(" and ", "where ");
            }
            String deleteQuery = "delete from " + tableName + " t " + whereClass;
            deleteQueriesList.add(deleteQuery);
        }
        return deleteQueriesList;
    }
}
