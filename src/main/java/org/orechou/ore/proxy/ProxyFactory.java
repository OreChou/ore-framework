package org.orechou.ore.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理工厂类
 *
 * @Author OreChou
 * @Create 2017-12-14 21:11
 */
public class ProxyFactory {

    public static <T> T createProxy(final Class<?> target, final List<Proxy> proxyList) {

        return (T) Enhancer.create(target, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return null;
            }
        });

    }

}
