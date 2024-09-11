package com.rockyshen.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Spring MVC中定义的统一返回结果类！
 * @author rockyshen
 * @date 2024/7/30 13:20
 */
@Data
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private String datetime;

    /**
     * 适用于：有业务Data，响应ok成功状态 + 实际业务数据的情况（ResultEnum)
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.OK.getCode());
        result.setMessage(ResultEnum.OK.getMessage());
        result.setData(data);
        result.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

    /**
     * 适用于：没有业务Data，只响应一个ok成功状态的情况（ResultEnum)
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.OK.getCode());
        result.setMessage(ResultEnum.OK.getMessage());
        result.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

    /**
     * 适用于：没有业务Data，只响应一个fail失败状态（ResultEnum)
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getMessage());
        result.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

    /**
     * 适用于：没有Data,传递了具体的HTTP状态码的响应，响应具体HTTP状态的code和message（statusCodeEnum)
     * @param statusCodeEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum){
        Result<T> result = new Result<>();
        result.setCode(statusCodeEnum.getCode());
        result.setMessage(statusCodeEnum.getMessage());
        result.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

    /**
     * 适用于：有Data（错误的）,且传递了具体的HTTP状态码的响应，响应具体HTTP状态的code和message 和 data（statusCodeEnum)
     * @param statusCodeEnum
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum, T data){
        Result<T> result = new Result<>();
        result.setCode(statusCodeEnum.getCode());
        result.setMessage(statusCodeEnum.getMessage());
        result.setData(data);
        result.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

}
