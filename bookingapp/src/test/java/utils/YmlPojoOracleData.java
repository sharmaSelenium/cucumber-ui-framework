package utils;

import java.util.HashMap;

public class YmlPojoOracleData {
    private String Table;
    private HashMap<String, Object> Column;

    public String getTable() {
        return this.Table;
    }

    public HashMap<String, Object> getColumn() {
        return this.Column;
    }

    public void setTable(String Table) {
        this.Table = Table;
    }

    public void setColumn(HashMap<String, Object> Column) {
        this.Column = Column;
    }
}
