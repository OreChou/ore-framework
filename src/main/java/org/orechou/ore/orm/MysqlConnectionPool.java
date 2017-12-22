package org.orechou.ore.orm;

import org.orechou.ore.bean.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Mysql连接池实现类
 *
 * @author OreChou
 * @create 2017-12-21 22:47
 */
public class MysqlConnectionPool implements ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConnectionPool.class);

    /**
     * 空闲的连接
     */
    private LinkedList<Connection> freeConnections = new LinkedList<>();

    /**
     * 活动的连接
     */
    private LinkedList<Connection> activeConnections = new LinkedList<>();

    /**
     * 当前线程获得的连接
     */
    private ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    /**
     * 定义一个用于放置数据库连接的局部线程变量（使每个线程都拥有自己的连接）
     */
    private static ThreadLocal<Connection> localConnection = new ThreadLocal<>();

    /**
     * 连接池可用状态
     */
    private boolean isActive = true;

    private DatabaseProperties properties;

    private MysqlConnectionPool() {
        super();
    }

    public static MysqlConnectionPool initConnectionPool(DatabaseProperties properties) {
        MysqlConnectionPool connectionPool = new MysqlConnectionPool();
        connectionPool.properties = properties;
        // 初始化连接池
        for (int i = 0; i < properties.getInitConnections(); i++) {
            try {
                Connection conn = connectionPool.newConnection();
                connectionPool.freeConnections.add(conn);
            } catch (SQLException | ClassNotFoundException e) {
                LOGGER.error("初始化连接池失败: 新增连接失败", e);
            }
        }
        connectionPool.isActive = true;
        return connectionPool;
    }

    @Override
    public synchronized Connection getConnection() {
        Connection conn = null;
        // 当活动连接数没有达到设置的最大连接数
        if (this.getActiveNumber() < properties.getMaxConnections()) {
            // 空闲连接列表不为空
            if (this.getFreeNumber() > 0) {
                conn = freeConnections.pollFirst();
                if (isValid(conn)) { // 若获取的连接有效
                    activeConnections.add(conn);
                    localConnection.set(conn);
                } else {
                    conn = getConnection(); // 同步方法是可重入锁
                }
            } else { // 空闲列表为空
                try {
                    conn = newConnection();
                    activeConnections.add(conn);
                } catch (SQLException | ClassNotFoundException e) {
                    LOGGER.error("新增连接失败");
                }
            }

        } else {
            long startTime = System.currentTimeMillis();

            // 进入等待。等待被notify(),notifyAll()唤醒或者超时自动苏醒
            try {
                this.wait(properties.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 若线程超时前没有获取连接，则返回null。若没有则重新连接
            // 如果timeout设置为0，就无限重连。
            if (System.currentTimeMillis() - startTime > properties.getTimeout()) {
                return null;
            }
            conn = this.getConnection();
        }

        return conn;
    }

    @Override
    public Connection getCurrentConnection() {
        Connection conn = localConnection.get();
        if (!isValid(conn)) {
            conn = getConnection();
        }
        return conn;
    }

    @Override
    public synchronized void releaseConnection(Connection conn) {
        activeConnections.remove(conn);
        localConnection.remove();
        if (isValid(conn)) {
            freeConnections.add(conn);
        } else {
            try {
                freeConnections.add(newConnection());
            } catch (SQLException | ClassNotFoundException e) {
                LOGGER.error("重新生成可用连接失败");
            }
        }
        // 唤醒getConnection()中等待的线程
        this.notifyAll();
    }

    @Override
    public synchronized void destroy() {
        for (Connection conn : freeConnections) {
            closeConnection(conn);
        }
        for (Connection conn : activeConnections) {
            closeConnection(conn);
        }
        isActive = false;
        freeConnections.clear();
        activeConnections.clear();
    }

    private void  closeConnection(Connection conn) {
        if (isValid(conn)) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public int getActiveNumber() {
        return activeConnections.size();
    }

    @Override
    public int getFreeNumber() {
        return freeConnections.size();
    }

    /**
     * 创建新的数据库连接
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private Connection newConnection() throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            Class.forName(properties.getDriverName());
            conn = DriverManager.getConnection(properties.getUrl(),
                    properties.getUsername(),
                    properties.getPassword());
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return conn;
    }

    /**
     * 检查一个连接是否有效
     * @param conn
     * @return
     * @throws SQLException
     */
    private Boolean isValid(Connection conn) {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
