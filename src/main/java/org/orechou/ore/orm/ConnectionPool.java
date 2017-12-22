package org.orechou.ore.orm;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接池接口
 *
 * @author OreChou
 * @create 2017-12-21 22:40
 */
public interface ConnectionPool {

    Connection getConnection();

    Connection getCurrentConnection();

    void releaseConnection(Connection conn);

    void destroy();

    boolean isActive();

    int getActiveNumber();

    int getFreeNumber();

}
