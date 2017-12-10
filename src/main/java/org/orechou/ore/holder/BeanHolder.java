package org.orechou.ore.holder;

import org.orechou.ore.utils.ReflectionUtil;

import java.util.HashMap;

/**
 * Bean实例的持有类
 *
 * @Author OreChou
 * @Create 2017-12-10 0:29
 */
public final class BeanHolder {

    /**
     * 一个HashMap，索引值为类类型，值为类实例
     */
    private final static HashMap<Class<?>, Object> BEAN_MAP;

    static {
        BEAN_MAP = new HashMap<>();
        // 遍历所有的Bean类，生成该类类型的实例，加入到Map中
        for (Class<?> clazz : ClassHolder.getClassSet()) {
            BEAN_MAP.put(clazz, ReflectionUtil.newInstance(clazz));
        }
    }

    /**
     * 根据提供的类类型，给出该类类型的实例
     * @param clazz 类类型
     * @return
     */
    public static Object getBeanInstance(Class<?> clazz) {
        return BEAN_MAP.get(clazz);
    }

    public static HashMap<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

}
