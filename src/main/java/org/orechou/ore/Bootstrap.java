package org.orechou.ore;

import org.orechou.ore.bean.Route;
import org.orechou.ore.holder.*;
import org.orechou.ore.utils.ClassLoaderUtils;

/**
 * 框架启动类
 *
 * @Author OreChou
 * @Create 2017-12-09 17:17
 */
public final class Bootstrap {

    public static void init() {
        Class<?>[] loadClasses = new Class<?>[]{
                PropertiesConfigHolder.class,
                ClassHolder.class,
                ControllerHolder.class,
                BeanHolder.class,
                IocHolder.class,
                AopHolder.class,
                ConnectionHolder.class
        };
        for (Class<?> clazz : loadClasses) {
            ClassLoaderUtils.loadClass(clazz.getName(), true);
        }
    }

    public static void main(String[] args) {
        init();
    }

}
