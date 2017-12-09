package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * Put 方法请求的注解
 *
 * @author OreChou
 * @create 2017-12-07 21:20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PutMapping {

    String[] value() default {};

}
