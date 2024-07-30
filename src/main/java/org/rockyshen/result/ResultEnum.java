package org.rockyshen.result;

/**
 * Result类中ok方法、fail方法的code枚举类
 * 最基本的code和message
 * @author rockyshen
 * @date 2024/7/30 13:28
 */
public enum ResultEnum {
    OK(0,"ok"),

    FAIL(1,"fail"),
    ;

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
