package org.orechou.ore.orm.utils;

import org.orechou.ore.holder.ConnectionHolder;
import org.orechou.ore.utils.NamingStringUtils;
import org.orechou.ore.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC的封装类
 *
 * @author OreChou
 * @create 2017-12-22 10:57
 */
public class JdbcUtils {

    /**
     * 数据库执行SQL语句，数据库连接由外部传入
     * @param sql
     * @param conn
     * @return
     */
    public static boolean executeSqlStatement(String sql, Connection conn) {
        boolean result = false;
        try {
            Statement statement = conn.createStatement();
            result = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据库插入操作
     * @param entity
     * @return
     */
    public static boolean insert(Object entity) {
        // 准备好执行的SQL和参数
        String sql = SqlUtils.insertRecordSQL(entity);
        List<Object> params = ParamUtils.generateInsertParams(entity);
        return executeWithoutResult(sql, params);
    }

    /**
     * 数据库修改操作
     * @param entity
     * @return
     */
    public static boolean update(Object entity) {
        String sql = SqlUtils.updateRecordSQL(entity);
        List<Object> params = ParamUtils.generateUpdateParams(entity);
        return executeWithoutResult(sql, params);
    }

    public static <T> List<T> selectAll(Object entity) {
        String sql = SqlUtils.queryRecordSQL(entity);
        List<Object> params = ParamUtils.generateSelectParams(entity);
        return query(sql, params, entity.getClass());
    }

    public static boolean delete(Object entity) {
        String sql = SqlUtils.deleteRecordSQL(entity);
        List<Object> params = ParamUtils.generateDeleteParams(entity);
        System.out.println(sql + " " + params.size());
        return executeWithoutResult(sql, params);
    }

    private static boolean executeWithoutResult(String sql, List<Object> params) {
        boolean result = false;
        // 1.获取数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            // 2.设置参数，执行SQL
            PreparedStatement statement = conn.prepareStatement(sql);
            setParams(statement, params);
            result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 3.释放获取的这个连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return result;
    }

    private static <T> List<T> query(String sql, List<Object> params, Class<?> clazz) {
        List<T> result = new ArrayList<>();
        // 1.获取数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            // 2.执行SQL获取结果
            PreparedStatement statement = conn.prepareStatement(sql);
            setParams(statement, params);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 3.处理返回的结果，通过反射生成类实例
            if (resultSet.next()) {
                try {
                    T record = (T) clazz.newInstance();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String fieldName = NamingStringUtils.underlineToCamel(metaData.getColumnName(i));
                        Object fieldValue = resultSet.getObject(i);
                        Field field = clazz.getDeclaredField(fieldName);
                        ReflectionUtil.setField(record, field, fieldValue);
                    }
                    result.add(record);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4.释放数据库连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return result;
    }

    private static void reflectSetValue() {

    }

    private static void setParams(PreparedStatement statement, List<Object> params) {
        for (int i = 0; i < params.size(); i++) {
            try {
                statement.setObject(i + 1, params.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
