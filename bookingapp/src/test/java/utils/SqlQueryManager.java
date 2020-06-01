package utils;

import static utils.Decoder.decode;
import static utils.YmlReader.createDeleteQueriesList;
import static utils.YmlReader.createInsertQueriesList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleTypes;

public class SqlQueryManager {

    public static void insertRow(String insertCondition) {
        List<String> insertQueriesList = createInsertQueriesList(insertCondition);
        for (String sqlQuery : insertQueriesList) {
            Connection connection = openOracleDbConnection();
            try {
                Statement statement = createSqlStatement(connection);
                executeSqlQuery(statement, sqlQuery);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeOracleDbConnection(connection);
            }
        }
    }

    public static void deleteRow(String deleteCondition) {
        List<String> deleteQueriesList = createDeleteQueriesList(deleteCondition);
        for (String sqlQuery : deleteQueriesList) {
            Connection connection = openOracleDbConnection();
            try {
                Statement statement = createSqlStatement(connection);
                executeSqlUpdate(statement, sqlQuery);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeOracleDbConnection(connection);
            }
        }
    }

    public static void callSproc(String sprocName, String dollarSepParams) {
        Connection connection = openOracleDbConnection();
        try {
            CallableStatement callableStatement = prepareSprocOracleConnection(connection, sprocName, dollarSepParams);
            executeCallableStatement(callableStatement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOracleDbConnection(connection);
        }
    }

    public static void insertRow(String tableName, HashMap<String, String> colValues) {
        String cols = "";
        String vals = "";
        for (Map.Entry<String, String> colValue : colValues.entrySet()) {
            cols = cols + ", " + colValue.getKey();
            vals = vals + ", " + colValue.getValue();
        }
        String sqlQuery = "Insert Into " + tableName + " (" + cols.replaceFirst(", ", "") + " ) Values ( " + vals.replaceFirst(", ", "") + " )";
        Connection connection = openOracleDbConnection();
        try {
            Statement statement = createSqlStatement(connection);
            executeSqlQuery(statement, sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOracleDbConnection(connection);
        }
    }

    public static void deleteRow(String tableName, HashMap<String, String> colValues) {
        String whereClass = "";
        for (Map.Entry<String, String> columnData : colValues.entrySet()) {
            String column = "t." + columnData.getKey();
            String value = columnData.getValue();
            whereClass = whereClass + " and " + column + " In " + value;
        }
        whereClass = whereClass.replaceFirst(" and ", "where ");
        String sqlQuery = "delete from " + tableName + " t " + whereClass;
        Connection connection = openOracleDbConnection();
        try {
            Statement statement = createSqlStatement(connection);
            executeSqlQuery(statement, sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOracleDbConnection(connection);
        }
    }

    private static Connection openOracleDbConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(getOracleConnectionUrl() + ":" + getOracleDataBase(), getOracleUserName(), getOraclePassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Statement createSqlStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    private static CallableStatement prepareSprocOracleConnection(Connection connection, String sprocName, String dollarSepParams) {
        String[] paramsArray = dollarSepParams.split("\\$");
        String callProcParams = "";
        if (!dollarSepParams.equals("")) {
            for (int ite = 0; ite < paramsArray.length; ite++) {
                callProcParams = callProcParams + ", ?";
            }
            callProcParams = callProcParams.replaceFirst(", ", "");
        }
        String callProc = "{ call " + sprocName + "(" + callProcParams + ") }";
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(callProc);
            if (!dollarSepParams.equals("")) {
                for (int ite = 0; ite < paramsArray.length; ite++) {
                    String paramType = paramsArray[ite].substring(0, 5);
                    switch (paramType) {
                        case "IPSTR": {
                            callableStatement.setString(ite + 1, paramsArray[ite].replaceFirst(paramType + "_", ""));
                            break;
                        }
                        case "IPINT": {
                            callableStatement.setInt(ite + 1, Integer.parseInt(paramsArray[ite].replaceFirst(paramType + "_", "")));
                            break;
                        }
                        case "OPINT": {
                            callableStatement.registerOutParameter(ite + 1, OracleTypes.INTEGER);
                            break;
                        }
                        case "OPSTR": {
                            callableStatement.registerOutParameter(ite + 1, OracleTypes.VARCHAR);
                            break;
                        }
                        case "OPCUR": {
                            callableStatement.registerOutParameter(ite + 1, OracleTypes.CURSOR);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callableStatement;
    }

    private static void executeSqlQuery(Statement statement, String sqlQuery) {
        try {
            statement.execute(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlUpdate(Statement statement, String sqlQuery) {
        try {
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeCallableStatement(CallableStatement callableStatement) {
        try {
            callableStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeOracleDbConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getOracleConnectionUrl() {
        if (System.getProperty("encodedCreds").equalsIgnoreCase("true")) {
            return decode(System.getProperty("oracleCreds")).split(" ")[0];
        }
        return System.getProperty("oracleCreds").split(" ")[0];
    }

    private static String getOracleDataBase() {
        if (System.getProperty("encodedCreds").equalsIgnoreCase("true")) {
            return decode(System.getProperty("oracleCreds")).split(" ")[1];
        }
        return System.getProperty("oracleCreds").split(" ")[1];
    }

    private static String getOracleUserName() {
        if (System.getProperty("encodedCreds").equalsIgnoreCase("true")) {
            return decode(System.getProperty("oracleCreds")).split(" ")[2];
        }
        return System.getProperty("oracleCreds").split(" ")[2];
    }

    private static String getOraclePassword() {
        if (System.getProperty("encodedCreds").equalsIgnoreCase("true")) {
            return decode(System.getProperty("oracleCreds")).split(" ")[3];
        }
        return System.getProperty("oracleCreds").split(" ")[3];
    }
}
