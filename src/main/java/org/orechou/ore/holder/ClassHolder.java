package org.orechou.ore.holder;

import org.orechou.ore.annotation.Controller;
import org.orechou.ore.annotation.Service;
import org.orechou.ore.utils.ClassLoaderUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类集合持有类
 *
 * @Author OreChou
 * @Create 2017-12-07 22:47
 */
public final class ClassHolder {

    /**
     * 所有类类型集合
     */
    private static final Set<Class<?>> CLASS_SET;

    /**
     * 所有控制类的类类型集合
     */
    private static final Set<Class<?>> CONTROLLER_CLASS_SET;

    /**
     * 所有服务类的类类型集合
     */
    private static final Set<Class<?>> SERVICE_CLASS_SET;

    static {
        CLASS_SET = ClassLoaderUtils.getClassFromPackage(PropertiesConfigHolder.getScanPackage());
        CONTROLLER_CLASS_SET = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                CONTROLLER_CLASS_SET.add(clazz);
            }
        }
        SERVICE_CLASS_SET = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                SERVICE_CLASS_SET.add(clazz);
            }
        }

    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getControllerClassSet() {
        return CONTROLLER_CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet() {return SERVICE_CLASS_SET;}

    /**
     * 获取应用包名下某父类（或者接口）的所有子类（或实现类）
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下某有注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

}
