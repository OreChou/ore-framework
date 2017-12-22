package org.orechou.ore.bean;

/**
 * 数据库的配置属性
 *
 * @author OreChou
 * @create 2017-12-22 0:13
 */
public class DatabaseProperties {

    /**
     * 数据库连接驱动
     */
    private String driverName;

    /**
     * 数据库连接url
     */
    private String url;

    /**
     * 数据库登陆用户名
     */
    private String username;

    /**
     * 数据库登陆密码
     */
    private String password;

    /**
     * 连接池最大连接数
     */
    private Integer maxConnections;

    /**
     * 连接池最小连接数
     */
    private Integer minConnections;

    /**
     * 连接池初始连接数
     */
    private Integer initConnections;

    /**
     * 重连时间间隔，单位毫秒
     */
    private Integer interval;

    /**
     * 连接超时时间，单位毫秒
     */
    private Integer timeout;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(Integer minConnections) {
        this.minConnections = minConnections;
    }

    public Integer getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(Integer initConnections) {
        this.initConnections = initConnections;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
