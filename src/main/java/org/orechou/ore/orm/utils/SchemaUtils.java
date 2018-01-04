package org.orechou.ore.orm.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表工具类
 *
 * @author OreChou
 * @create 2018-01-03 13:35
 */
public class SchemaUtils {

    /**
     * 根据传入的数据库连接，获取所有的数据表名称
     * @param conn
     * @return
     */
    public static List<String> allTableNames(Connection conn) {
        return listTableName(conn, null,null,"%", new String[] {"TABLE"});
    }

    /**
     * 根据传入的参数，获取所有满足条件的数据表名称
     * @param conn
     * @param catalog
     * @param schemaPattern
     * @param tableNamePattern
     * @param types
     * @return
     */
    private static List<String> listTableName(Connection conn, String catalog, String schemaPattern, String tableNamePattern, String types[]) {
        List<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet resultSet = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

}
