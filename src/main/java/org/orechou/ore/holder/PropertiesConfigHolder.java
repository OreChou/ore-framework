package org.orechou.ore.holder;

import org.orechou.ore.bean.PropertiesConfig;
import org.yaml.snakeyaml.Yaml;

/**
 * 属性配置的持有类
 *
 * @Author OreChou
 * @Create 2017-12-10 11:12
 */
public final class PropertiesConfigHolder {

    public final static PropertiesConfig PROPERTIES_CONFIG;

    static {
        Yaml yaml = new Yaml();
        PROPERTIES_CONFIG = yaml.loadAs(Thread.currentThread().getContextClassLoader().getResourceAsStream("ore.yaml"), PropertiesConfig.class);
    }

    /**
     * 获取扫描的基础包名
     * @return
     */
    public static String getScanPackage() {
        return PROPERTIES_CONFIG.getBase().getBasePackage();
    }

}
