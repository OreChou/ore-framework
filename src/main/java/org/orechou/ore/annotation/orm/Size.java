package org.orechou.ore.annotation.orm;

import java.lang.annotation.*;

/**
 * @author OreChou
 * @create 2017-12-21 21:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Size {

    String value() default "31";

}
