package org.orechou.ore.holder;

import org.orechou.ore.annotation.Controller;
import org.orechou.ore.utils.ClassLoaderUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 类集合持有类
 *
 * @Author OreChou
 * @Create 2017-12-07 22:47
 */
public final class ClassHolder {

    private static final Set<Class<?>> CLASS_SET;

    private static final Set<Class<?>> CONTROLLER_CLASS_SET;

    static {
        CLASS_SET = ClassLoaderUtils.getClassFromPackage("org.orechou.ore.test");
        CONTROLLER_CLASS_SET = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                CONTROLLER_CLASS_SET.add(clazz);
            }
        }

    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getControllerClassSet() {
        return CONTROLLER_CLASS_SET;
    }

}
