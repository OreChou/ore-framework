package org.orechou.ore.bean;

import org.orechou.ore.enums.Method;

import java.util.Objects;

/**
 * 封装的请求
 *
 * @Author OreChou
 * @Create 2017-12-07 23:20
 */
public class Route {

    /**
     * HTTP url
     */
    private String path;

    /**
     * HTTP method
     */
    private Method method;

    public Route(String path, Method method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return Objects.equals(getPath(), route.getPath()) &&
                getMethod() == route.getMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getMethod());
    }

    @Override
    public String toString() {
        return "Route{" +
                "path='" + path + '\'' +
                ", method=" + method +
                '}';
    }
}
