package org.orechou.ore.annotation;

import org.orechou.ore.enums.Method;

import java.lang.annotation.*;

/**
 * 请求方法的注解
 *
 * @author OreChou
 * @create 2017-12-07 21:15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String[] value() default {};

    Method[] method() default {};

}
