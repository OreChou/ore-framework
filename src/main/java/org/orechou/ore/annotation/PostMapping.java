package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * Post 方法请求的注解
 *
 * @Author OreChou
 * @Create 2017-12-07 21:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {
}
