package com.rockyshen.result;

/**
 * HTTP状态枚举类
 * @author rockyshen
 * @date 2024/7/30 13:56
 */
public enum StatusCodeEnum {
    SUCCESS(200,"success"),
    ERR_INTERNAL_SERCER(500, "internal server error"),
    ERR_INPUT_INVALID(8020, "input invalid"),
    ERR_LOGIN(10000, "login fail")

    // 参考HTTP状态码表，自定义自己的
    ;

    private final Integer code;

    private final String message;

    StatusCodeEnum(Integer code, String message) {
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
