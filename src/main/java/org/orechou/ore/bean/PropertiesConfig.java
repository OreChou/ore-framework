package org.orechou.ore.bean;

/**
 * 属性的配置类
 *
 * @Author OreChou
 * @Create 2017-12-10 10:57
 */
public class PropertiesConfig {

    private BaseProperties base;

    private DatabaseProperties database;

    public BaseProperties getBase() {
        return base;
    }

    public void setBase(BaseProperties base) {
        this.base = base;
    }

    public DatabaseProperties getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseProperties database) {
        this.database = database;
    }
}
