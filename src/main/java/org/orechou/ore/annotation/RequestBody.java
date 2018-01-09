package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * 用于将提交的json数据转成一个Java对象
 *
 * @author OreChou
 * @create 2018-01-05 22:46
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {
}
