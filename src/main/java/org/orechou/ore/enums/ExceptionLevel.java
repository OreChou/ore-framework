package org.orechou.ore.enums;

/**
 * 异常等级
 *
 * @Author OreChou
 * @Create 2017-12-09 16:39
 */
public enum ExceptionLevel {

    /**
     * framework: 框架异常，用于框架设计而产生的异常
     */
    FRAMEWORK,
    /**
     * system: 系统异常，用于在基于该框架二次编码的过程中产生的异常
     */
    SYSTEM,
    /**
     * business: 业务异常，用于业务逻辑错误而产生的异常
     */
    BUSINESS

}
