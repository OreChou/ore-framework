package org.orechou.ore;

import org.orechou.ore.bean.Route;
import org.orechou.ore.holder.ClassHolder;
import org.orechou.ore.holder.ControllerHolder;
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
                ClassHolder.class,
                ControllerHolder.class
        };
        for (Class<?> clazz : loadClasses) {
            ClassLoaderUtils.loadClass(clazz.getName(), false);
        }
        System.out.println(ClassHolder.getClassSet().size());
        System.out.println(ControllerHolder.ROUTE_HANDLER.size());
        for (Route route : ControllerHolder.ROUTE_HANDLER.keySet()) {
            System.out.println(route.toString());
        }

    }

    public static void main(String[] args) {
        init();
    }

}
