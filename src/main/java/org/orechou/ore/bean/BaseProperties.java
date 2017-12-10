package org.orechou.ore.bean;

/**
 * 基础的配置属性
 *
 * @Author OreChou
 * @Create 2017-12-10 11:05
 */
public class BaseProperties {

    /**
     * base-url
     */
    private String baseUrl;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 扫描的基础包
     */
    private String basePackage;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
