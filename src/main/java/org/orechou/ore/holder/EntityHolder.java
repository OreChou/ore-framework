package org.orechou.ore.holder;

import org.orechou.ore.utils.NamingStringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity的持有类
 *
 * @author OreChou
 * @create 2017-12-21 19:17
 */
public final class EntityHolder {

    /**
     * Key: 实体类类型
     * Value: 实体类属性名称与数据表列名的对应关系
     */
    public final static Map<Class<?>, Map<String, String>> entityMap;

    /**
     * Key: 数据表名
     * Value: 数据表列名与实体类属性的对应关系
     */
    public final static Map<String, Map<String, String>> tableMap;

    static {
        entityMap = new HashMap<>();
        tableMap = new HashMap<>();
        for (Class<?> clazz : ClassHolder.getEntityClassSet()) {
            // 转换成下划线的表名
            String tableName = NamingStringUtils.camelToUnderline(clazz.getSimpleName());
            Map<String, String> fieldMap = new HashMap<>();
            Map<String, String> columnMap = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                String classFieldName = field.getName();
                String tableColumnName = NamingStringUtils.camelToUnderline(classFieldName);
                fieldMap.put(classFieldName, tableColumnName);
                columnMap.put(tableColumnName, classFieldName);
            }
            entityMap.put(clazz, fieldMap);
            tableMap.put(tableName, columnMap);
        }
    }

}
