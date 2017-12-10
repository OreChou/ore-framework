package org.orechou.ore.utils;

import org.orechou.ore.bean.Params;
import org.orechou.ore.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具，封装了Java反射方法
 *
 * @Author OreChou
 * @Create 2017-12-09 16:33
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    private static final String NEW_INSTANCE_FAIL = "New instance failure";

    private static final String INVOKE_METHOD_FAIL = "Invoke method failure";

    private static final String SET_FIELD_FAIL = "Set field value failure";

    private static final String GET_FIELD_FAIL = "Get field value failure";

    /**
     * 实例化一个类
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error(NEW_INSTANCE_FAIL, e);
            throw new BaseException(NEW_INSTANCE_FAIL);
        }
        return instance;
    }

    /**
     * 调用一个类对象的方法
     * @param classObj 类的实例
     * @param method 方法
     * @param params 参数
     * @return
     */
    private static Object invokeMethod(Object classObj, Method method, Object... params) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(classObj, params);
        } catch (Exception e) {
            LOGGER.error(INVOKE_METHOD_FAIL, e);
            throw new BaseException(INVOKE_METHOD_FAIL);
        }
        return result;
    }

    /**
     * 调用一个类对象的方法
     * @param classObj 类的实例
     * @param method 方法
     * @param params 封装的参数类
     * @return
     */
    public static Object invokeMethod(Object classObj, Method method, Params params) {
        Object result;
        List<String> parameterNames = getParameterNameList(method);
        List<Object> parameterObjects = new ArrayList<>();
        for (String name : parameterNames) {
            System.out.println(name);
            parameterObjects.add(params.get(name));
        }

//        String[] arr = list.toArray(new String[list.size()]);
        result = invokeMethod(classObj, method, parameterObjects.toArray(new String[parameterObjects.size()]));
        return result;
    }

    /**
     * 为一个类对象的属性设值
     * @param classObj 类的实例
     * @param field 属性域
     * @param value 值
     */
    public static void setField(Object classObj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(classObj, value);
        } catch (Exception e) {
            LOGGER.error(SET_FIELD_FAIL, e);
            throw new BaseException(SET_FIELD_FAIL);
        }
    }

    /**
     * 获取一个类对象指定属性的值
     * @param classObj 类的实例
     * @param field 属性域
     */
    public static void getField(Object classObj, Field field) {
        Object value;
        try {
            field.setAccessible(true);
            field.get(classObj);
        } catch (Exception e) {
            LOGGER.error(GET_FIELD_FAIL, e);
            throw new BaseException(GET_FIELD_FAIL);
        }
    }

    /**
     * 获取一个方法传入参数的名称
     * 该方法只在jdk8以上有效，并且编译时要打开 -parameters 选项
     * @param method 传入的方法
     * @return
     */
    public static List<String> getParameterNameList(Method method) {
        List<String> parameters = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            parameters.add(parameter.getName());
        }
        return parameters;
    }

}
