package org.orechou.ore.proxy;

/**
 * 代理的接口
 *
 * @Author OreChou
 * @Create 2017-12-14 21:07
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
