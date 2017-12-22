package org.orechou.ore.orm;

import org.orechou.ore.holder.ConnectionHolder;
import org.orechou.ore.utils.NamingStringUtils;
import org.orechou.ore.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

/**
 * JDBC的封装类
 *
 * @author OreChou
 * @create 2017-12-22 10:57
 */
public class JdbcUtils {

    /**
     * 获取单个的
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public <T>T getSingleResult(String sql, List<Object> params) {
        T result = null;
        // 获取一个数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (resultSet.next()) {
                // 创建实例
                Class<?> clazz = result.getClass();
                result = (T) clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // 获取数据表字段的名称
                    String columnName = metaData.getColumnName(i + 1);
                    // 将下划线的表字段转换成驼峰的实体属性名称
                    String fieldName = NamingStringUtils.underlineToCamel(columnName);
                    Object fieldValue = resultSet.getObject(columnName);
                    Field field = clazz.getDeclaredField(fieldName);
                    // 通过反射设置Field的值
                    ReflectionUtil.setField(result, field, fieldValue);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // 释放获取的这个连接
        ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        return result;
    }

}
