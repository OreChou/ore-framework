package org.orechou.ore.holder;

import org.orechou.ore.annotation.Aspect;
import org.orechou.ore.proxy.AspectProxy;
import org.orechou.ore.proxy.Proxy;
import org.orechou.ore.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Aop的持有类
 *
 * @Author OreChou
 * @Create 2017-12-14 23:07
 */
public final class AopHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHolder.class);

    static {
        try {
            // 代理类 - 被代理类的关系映射
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
                BeanHolder.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            LOGGER.error("Aop failure", e);
        }
    }

    /**
     * 构建代理类的映射
     * @return
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        // 获取所有继承于AspectProxy的子类
        Set<Class<?>> proxyClassSet = ClassHolder.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            // 切面的代理类不仅要继承于AspectProxy，还要标有@Aspect的注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 根据传入的注解，获取所代理的目标类（指定了相应注解）集合
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHolder.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 根据传入的代理类 - 被代理类的关系映射，生成每个被代理类和其代理实例的关系映射
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        // 每个被代理类和其代理列表的映射关系
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            // 获得代理类类类型
            Class<?> proxyClass = proxyEntry.getKey();
            // 获得被代理类类类型列表
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            // 遍历被代理类
            for (Class<?> targetClass : targetClassSet) {
                // 生成代理类的实例
                Proxy proxy = (Proxy) proxyClass.newInstance();
                // 构建每个被代理类的代理列表
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

}
