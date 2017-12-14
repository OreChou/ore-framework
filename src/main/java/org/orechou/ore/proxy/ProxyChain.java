package org.orechou.ore.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 链式代理
 * 将多个代理串链在一起，一个一个的去执行，执行的顺序取决于链上的先后顺序
 * JDK的动态代理只能代理实现了接口的类实例，而CGLib则可以代理普通的类实例
 *
 * @Author OreChou
 * @Create 2017-12-14 21:31
 */
public class ProxyChain {

    /**
     * 被代理类类型
     */
    private final Class<?> targetClass;

    /**
     * 被代理类实例
     */
    private final Object targetObject;

    /**
     * 被代理类方法
     */
    private final Method targetMethod;

    /**
     * 被代理类方法的参数
     */
    private final Object[] methodParams;

    /**
     * CBLib提供的方法代理类
     */
    private final MethodProxy methodProxy;

    /**
     * 代理链
     */
    private List<Proxy> proxyList = new ArrayList<>();

    /**
     * 指向代理链当前执行的位置
     */
    private int proxyIndex = 0;

    /**
     * 用于CGLib
     */
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    /**
     * 用于JDK
     */
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
        this.methodProxy = null;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    /**
     * 没有执行链式的最后 则依次执行链式代理
     * 使用proxyIndex充当计数器，如果没到最后，则取出响应的Proxy对象
     * 并调用doProxy方法，在Proxy接口的实现中提供相应的横切逻辑，并再次代用doProxyChain直到次数达到
     * 最后，调用methodProxy的invokeSuper(),执行目标对象的业务逻辑
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return result;
    }

}
