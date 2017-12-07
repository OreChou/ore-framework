package org.orechou.ore.bean;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求对应的处理对象
 *
 * @Author OreChou
 * @Create 2017-12-07 23:26
 */
public class Handler {

    /**
     * 对应的 controller 类
     */
    private Class<?> controllerClass;

    /**
     * 对应的 controller 中对应的方法
     */
    private Method method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
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
        if (!(o instanceof Handler)) return false;
        Handler handler = (Handler) o;
        return Objects.equals(getControllerClass(), handler.getControllerClass()) &&
                Objects.equals(getMethod(), handler.getMethod());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getControllerClass(), getMethod());
    }
}
