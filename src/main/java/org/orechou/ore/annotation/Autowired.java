package org.orechou.ore.annotation;

import java.lang.annotation.*;

/**
 * 注入注解
 *
 * @Author OreChou
 * @Create 2017-12-07 21:11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
