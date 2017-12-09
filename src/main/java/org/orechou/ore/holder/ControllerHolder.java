package org.orechou.ore.holder;

import org.orechou.ore.annotation.*;
import org.orechou.ore.bean.Handler;
import org.orechou.ore.bean.Route;
import org.orechou.ore.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 控制器的持有类
 * TODO: static里面应该要为url加上正则表达式
 * @Author OreChou
 * @Create 2017-12-07 23:14
 */
public final class ControllerHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHolder.class);

    /**
     * 用于保存所有Controller中定义的路由与处理函数
     * 路由的URL作为key
     */
    public final static HashMap<Route, Handler> ROUTE_HANDLER;

    static {
        ROUTE_HANDLER = new HashMap<>();
        for (Class clazz : ClassHolder.getControllerClassSet()) {
            // 获取控制器上面的注解，并将url的一部分取出
            RequestMapping classRequestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            String[] clazzUrls = classRequestMapping.value();
            // 外层循环遍历控制器指定了url部分
            for (String clazzUrl : clazzUrls) {
                // 遍历所有的方法；getDeclaredMethods会返回了除了继承以外的所有声明的方法
                for (Method method : clazz.getDeclaredMethods()) {
                    // 获取到方法上的url和所声明的http方法
                    String[] methodUrls = null;
                    org.orechou.ore.enums.Method[] httpMethods = null;
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        methodUrls = requestMapping.value();
                        httpMethods = requestMapping.method();
                    } else if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        methodUrls = getMapping.value();
                        httpMethods = new org.orechou.ore.enums.Method[] {org.orechou.ore.enums.Method.GET};
                    } else if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        methodUrls = postMapping.value();
                        httpMethods = new org.orechou.ore.enums.Method[] {org.orechou.ore.enums.Method.POST};
                    } else if (method.isAnnotationPresent(PutMapping.class)) {
                        PutMapping putMapping = method.getAnnotation(PutMapping.class);
                        methodUrls = putMapping.value();
                        httpMethods = new org.orechou.ore.enums.Method[] {org.orechou.ore.enums.Method.PUT};
                    } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
                        methodUrls = deleteMapping.value();
                        httpMethods = new org.orechou.ore.enums.Method[] {org.orechou.ore.enums.Method.DELETE};
                    }
                    for (String methodUrl : methodUrls) {
                        for (org.orechou.ore.enums.Method httpMethod : httpMethods) {
                            Route route = new Route(clazzUrl + methodUrl, httpMethod);
                            if (ROUTE_HANDLER.containsKey(route)) {
                                LOGGER.error("定义了重复的路由");
                                throw new BaseException("定义了重复的路由");
                            } else {
                                // 若 ROUTE_HANDLER 不存在，则将这一对加入到其中
                                Handler handler = new Handler(clazz, method);
                                ROUTE_HANDLER.put(route, handler);
                            }
                        }
                    }

                }
            }
        }
    }

}
