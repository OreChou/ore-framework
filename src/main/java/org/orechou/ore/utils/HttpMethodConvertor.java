package org.orechou.ore.utils;

import org.orechou.ore.enums.Method;

/**
 * HTTP方法转换器，将字符串转换成枚举值
 *
 * @Author OreChou
 * @Create 2017-12-07 23:32
 */
public class HttpMethodConvertor {

    public static Method convert(String str) {
        Method method = Method.GET;
        switch (str.trim().toUpperCase()) {
            case "GET":
                method = Method.GET;
                break;
            case "POST":
                method = Method.POST;
                break;
            case "PUT":
                method = Method.PUT;
                break;
            case "DELETE":
                method = Method.DELETE;
                break;
        }
        return method;
    }

}
