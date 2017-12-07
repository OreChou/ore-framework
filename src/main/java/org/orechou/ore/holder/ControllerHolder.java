package org.orechou.ore.holder;

import org.orechou.ore.annotation.RequestMapping;
import org.orechou.ore.bean.Handler;
import org.orechou.ore.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * 控制器的持有类
 *
 * @Author OreChou
 * @Create 2017-12-07 23:14
 */
public class ControllerHolder {

    public static HashMap<Request, Handler> REQUEST_HANDLER;

    public static Set<Class<?>> CLASS_SET;

    static {

        REQUEST_HANDLER = new HashMap<>();
        for (Class clazz : CLASS_SET) {
            // 获取控制器上面的注解，并将url的一部分取出
            RequestMapping classRequestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            String[] clazzUrls = classRequestMapping.value();


            for (Method method : clazz.getMethods()) {

                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    //
                    String[] values = requestMapping.value();
                    org.orechou.ore.enums.Method[] httpMethods = requestMapping.method();

                    for (String value : values) {
                        for (org.orechou.ore.enums.Method httpMethod : httpMethods) {
                            Request request = new Request(value, httpMethod);
                            if (REQUEST_HANDLER.containsKey(request)) {
                                System.out.println("定义了重复的路由");
                                throw new RuntimeException();
                            }
                        }
                    }

                }

            }

        }


    }

}
