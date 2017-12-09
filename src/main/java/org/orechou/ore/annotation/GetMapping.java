package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * Get 方法请求的注解
 *
 * @Author OreChou
 * @Create 2017-12-07 21:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {

    String[] value() default {};

}
