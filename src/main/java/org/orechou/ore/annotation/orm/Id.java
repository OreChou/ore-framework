package org.orechou.ore.annotation.orm;

import java.lang.annotation.*;

/**
 * 数据库实体Id的注解类
 *
 * @author OreChou
 * @create 2017-12-21 20:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {

    String name() default "";

}
