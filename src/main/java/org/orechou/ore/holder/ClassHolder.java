package org.orechou.ore.holder;

import org.orechou.ore.utils.ClassLoaderUtils;

import java.util.Set;

/**
 * 类集合持有类
 *
 * @Author OreChou
 * @Create 2017-12-07 22:47
 */
public class ClassHolder {

    private static final Set<Class<?>> CLASS_SET;

    static {
        CLASS_SET = ClassLoaderUtils.getClassFromPackage("");
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

}
