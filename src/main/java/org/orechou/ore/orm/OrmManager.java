package org.orechou.ore.orm;

import org.orechou.ore.holder.ConnectionHolder;
import org.orechou.ore.holder.EntityHolder;
import org.orechou.ore.orm.utils.JdbcUtils;
import org.orechou.ore.orm.utils.SchemaUtils;
import org.orechou.ore.orm.utils.SqlUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Orm管理器
 * 功能：检测数据
 *
 * @author OreChou
 * @create 2018-01-03 13:14
 */
public final class OrmManager {

    private static List<String> existTableNames = new ArrayList<>();

    static {
        // 1.获取连接
        Connection conn = ConnectionHolder.mysqlConnectionPool.getConnection();
        existTableNames = SchemaUtils.allTableNames(conn);
        // 2.遍历table-entity映射集合，创建数据库中没有的table
        for (Map.Entry<String, Class<?>> tableEntityEntry : EntityHolder.tableEntityMap.entrySet()) {
            String table = tableEntityEntry.getKey();
            // 将未在数据库中创建表的实体类类型提取出来
            if (!existTableNames.contains(table)) {
                Class<?> clazz = tableEntityEntry.getValue();
                String createTableSQL = SqlUtils.createTableSQL(clazz);
                JdbcUtils.executeSqlStatement(createTableSQL, conn);
            }
        }
        // 3.释放连接
        ConnectionHolder.mysqlConnectionPool.releaseConnection(conn);
    }

}
