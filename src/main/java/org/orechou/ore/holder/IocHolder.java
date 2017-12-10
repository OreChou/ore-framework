package org.orechou.ore.holder;

import org.orechou.ore.annotation.Autowired;
import org.orechou.ore.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入的实现类
 *
 * @Author OreChou
 * @Create 2017-12-10 11:42
 */
public final class IocHolder {

    static {
        // 遍历所有的类类型和实例对
        for (Map.Entry<Class<?>, Object> beanEntry : BeanHolder.getBeanMap().entrySet()) {
            Class<?> clazz = beanEntry.getKey();
            Object instance = beanEntry.getValue();
            for (Field field : clazz.getDeclaredFields()) {
                // 若该类的属性域上面有自动注入的注解，则将注入的类的实例利用反射的方式设置
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> injectClass = field.getType();
                    ReflectionUtil.setField(instance, field, BeanHolder.getBeanInstance(injectClass));
                }
            }

        }
    }

}
