package org.orechou.ore.exception;

import org.orechou.ore.enums.ExceptionLevel;

/**
 * 基础的异常继承与RuntimeException
 *
 * @Author OreChou
 * @Create 2017-12-09 16:35
 */
public class BaseException extends RuntimeException {

    /**
     * 异常等级
     */
    private ExceptionLevel level;

    public BaseException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param level 异常级别
     */
    public BaseException(ExceptionLevel level) {
        super();
        this.level = level;
    }

    /**
     * 构造函数
     * @param message 异常的相关消息
     * @param level 异常级别
     */
    public BaseException(String message, ExceptionLevel level) {
        super(message);
        this.level = level;
    }

    /**
     * 构造函数
     * @param cause 原因
     * @param level 异常级别
     */
    public BaseException(Throwable cause, ExceptionLevel level) {
        super(cause);
        this.level = level;
    }

    public BaseException(String message, Throwable cause, ExceptionLevel level) {
        super(message, cause);
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(super.toString()).append(" - Level: ");
        switch (this.level) {
            case FRAMEWORK:
                buffer.append("framework");
                break;
            case SYSTEM:
                buffer.append("system");
                break;
            case BUSINESS:
                buffer.append("business");
                break;
            default:
                buffer.append("unknown");
                break;
        }
        return buffer.toString();
    }

}
