package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * 控制器注解
 *
 * @Author OreChou
 * @Create 2017-12-07 21:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    String[] value() default {};

}
