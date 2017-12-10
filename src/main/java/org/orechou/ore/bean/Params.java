package org.orechou.ore.bean;

import java.util.HashMap;

/**
 * HTTP请求的参数
 *
 * @Author OreChou
 * @Create 2017-12-10 0:52
 */
public class Params {

    private HashMap<String, Object> paramMap = new HashMap<String, Object>();

    public void put(String name, Object value) {
        paramMap.put(name, value);
    }

    public Object get(String name) {
        return paramMap.get(name);
    }

}
