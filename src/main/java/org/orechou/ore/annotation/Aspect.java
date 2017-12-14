package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @Author OreChou
 * @Create 2017-12-14 21:04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {

    /**
     * 注解, 用来定义Controller这类注解
     */
    Class<? extends Annotation> value();

}
