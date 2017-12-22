package org.orechou.ore.holder;

import org.orechou.ore.orm.MysqlConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池持有类
 *
 * @author OreChou
 * @create 2017-12-22 10:25
 */
public final class ConnectionHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHolder.class);

    public static MysqlConnectionPool mysqlConnectionPool;

    static {
        mysqlConnectionPool = MysqlConnectionPool.initConnectionPool(PropertiesConfigHolder.PROPERTIES_CONFIG.getDatabase());
    }

}
