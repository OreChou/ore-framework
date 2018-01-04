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
     * 执行SQL，返回结果列表,
     * @param sql sql语句
     * @param params 参数
     * @param clazz 返回结果的类类型
     * @param <T>
     * @return
     */
    public static <T>List<T> executeWithResults(String sql, List<Object> params, Class<?> clazz) {
        List<T> results = new ArrayList<>();
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
            while (resultSet.next()) {
                T item = (T) clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // 获取数据表字段的名称
                    String columnName = metaData.getColumnName(i + 1);
                    // 将下划线的表字段转换成驼峰的实体属性名称
                    String fieldName = NamingStringUtils.underlineToCamel(columnName);
                    Object fieldValue = resultSet.getObject(columnName);
                    Field field = clazz.getDeclaredField(fieldName);
                    // 通过反射设置Field的值
                    ReflectionUtil.setField(item, field, fieldValue);
                }
                results.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            // 释放获取的这个连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return results;
    }

    /**
     * 执行SQL，返回单个的结果
     * @param sql
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T executeWithSingleResult(String sql, List<Object> params, Class<?> clazz) {
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
        } finally {
            // 释放获取的这个连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return result;
    }

    public static boolean executeWithNoResult(String sql, List<Object> params) {
        boolean result = false;
        // 获取一个数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }
            result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放获取的这个连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return  result;
    }

    /**
     * 数据库执行SQL语句
     * @param sql 传入的SQL语句
     * @return
     */
    public static boolean executeSqlStatement(String sql) {
        boolean result = false;
        // 获取一个数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            Statement statement = conn.createStatement();
            result = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放获取的这个连接
            ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
        }
        return result;
    }

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
        return insertOrUpdate(sql, params);
    }

    /**
     * 数据库修改操作
     * @param entity
     * @return
     */
    public static boolean update(Object entity) {
        String sql = SqlUtils.updateRecordSQL(entity);
        List<Object> params = ParamUtils.generateUpdateParams(entity);
        return insertOrUpdate(sql, params);
    }

    public static <T> List<T> selectAll(Object entity) {
        List<T> result = new ArrayList<>();
        String sql = SqlUtils.queryRecordSQL(entity);
        List<Object> params = ParamUtils.generateUpdateParams(entity);

        return result;
    }


    private static boolean insertOrUpdate(String sql, List<Object> params) {
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

    private static <T> List<T> query(String sql, List<Object> params) {
        List<T> result = new ArrayList<>();

        // 1.获取数据库连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            setParams(statement, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
