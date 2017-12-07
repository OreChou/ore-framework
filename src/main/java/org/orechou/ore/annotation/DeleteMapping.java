package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * Delete 方法请求的注解
 *
 * @author OreChou
 * @create 2017-12-07 21:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteMapping {
}
