package org.orechou.ore.orm;

/**
 * 数据类型转换器
 *
 * @author OreChou
 * @create 2017-12-21 20:16
 */
public interface TypeConvertor {

    /**
     * 将数据库的数据类型转换成Java的数据类型
     * @param type
     * @return
     */
    String databaseTypeToJavaType(String type);

    /**
     * 将Java的数据类型转换成数据库的数据类型
     * @param type
     * @return
     */
     String javaTypeToDatabaseType(String type);
}
