package org.orechou.ore.annotation.orm;

import java.lang.annotation.*;

/**
 * 数据库的实体类
 *
 * @author OreChou
 * @create 2017-12-21 19:06
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {
}
