package org.orechou.ore.orm;

import java.util.HashMap;
import java.util.Map;

/**
 * Mysql数据类型转换器
 *
 * @author OreChou
 * @create 2017-12-21 20:18
 */
public final class MysqlTypeConvertor implements TypeConvertor {

    /**
     * 数据库类型到Java类型的映射表
     */
    private static Map<String, String> dbTypeToJavaTypeMap = new HashMap<>();

    /**
     * Java类型到数据库类型的映射表
     */
    private static Map<String, String> javaTypeToDbTypeMap = new HashMap<>();

    static {
        dbTypeToJavaTypeMap.put("tinyint", "Boolean");
        dbTypeToJavaTypeMap.put("smallint", "Integer");
        dbTypeToJavaTypeMap.put("mediumint", "Integer");
        dbTypeToJavaTypeMap.put("int", "Integer");
        dbTypeToJavaTypeMap.put("bigint", "Long");
        dbTypeToJavaTypeMap.put("float", "Double");
        dbTypeToJavaTypeMap.put("double", "Double");
        dbTypeToJavaTypeMap.put("clob", "java.sql.Clob");
        dbTypeToJavaTypeMap.put("blob", "java.sql.Blob");
        dbTypeToJavaTypeMap.put("date", "java.util.Date");
        dbTypeToJavaTypeMap.put("time", "java.sql.Time");
        dbTypeToJavaTypeMap.put("timestamp", "java.util.Date");
        dbTypeToJavaTypeMap.put("datetime", "java.util.Date");
        dbTypeToJavaTypeMap.put("varchar", "String");
        dbTypeToJavaTypeMap.put("char", "String");

        javaTypeToDbTypeMap.put("Boolean", "tinyint");
        javaTypeToDbTypeMap.put("Integer", "int");
        javaTypeToDbTypeMap.put("Long", "bigint");
        javaTypeToDbTypeMap.put("Date", "datetime");
        javaTypeToDbTypeMap.put("String", "varchar");
        javaTypeToDbTypeMap.put("Double", "double");
        javaTypeToDbTypeMap.put("Float", "double");
    }

    @Override
    public String databaseTypeToJavaType(String type) {
        return dbTypeToJavaTypeMap.get(type);
    }

    @Override
    public String javaTypeToDatabaseType(String type) {
        return javaTypeToDbTypeMap.get(type);
    }
}
