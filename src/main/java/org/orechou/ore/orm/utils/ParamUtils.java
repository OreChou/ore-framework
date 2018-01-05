package org.orechou.ore.orm.utils;

import org.orechou.ore.annotation.orm.Id;
import org.orechou.ore.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数工具类
 *
 * @author OreChou
 * @create 2018-01-03 17:43
 */
public final class ParamUtils {

    /**
     * 生成用于数据库插入记录的参数
     * @param entity
     * @return
     */
    public static List<Object> generateInsertParams(Object entity) {
        List<Object> params = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(entity, field);
            if (value != null) {
                if (field.isAnnotationPresent(Id.class)) {
                    params.add(0, value);
                } else {
                    params.add(value);
                }
            }
        }
        return params;
    }

    /**
     * 生成用于数据库修改记录的参数
     * @param entity
     * @return
     */
    public static List<Object> generateUpdateParams(Object entity) {
        List<Object> params = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(entity, field);
            if (value != null) {
                if (!field.isAnnotationPresent(Id.class)) {
                    params.add(value);
                }
            }
        }
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(entity, field);
            if (value != null) {
                if (field.isAnnotationPresent(Id.class)) {
                    params.add(value);
                }
            }
        }
        return params;
    }

    /**
     * 生成用于数据库查询的参数
     * @param entity
     * @return
     */
    public static List<Object> generateSelectParams(Object entity) {
        return generateSelectOrDeleteParams(entity);
    }

    public static List<Object> generateDeleteParams(Object entity) {
        return generateSelectOrDeleteParams(entity);
    }

    private static List<Object> generateSelectOrDeleteParams(Object entity) {
        List<Object> params = new ArrayList<>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = ReflectionUtil.getField(entity, field);
            if (value != null) {
                params.add(value);
            }
        }
        return params;
    }

}
