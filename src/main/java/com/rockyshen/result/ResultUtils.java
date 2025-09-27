package com.rockyshen.result;

/**
 * 统一返回结果工具类
 * @author rockyshen
 * @date 2024/9/10 13:36
 */
public class ResultUtils {

    /**
     * 静态方法，成功的情况
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data){
        return new Result<>(StatusCodeEnum.SUCCESS.getCode(),StatusCodeEnum.SUCCESS.getMsg(), data,"");
    }

    /**
     * 静态方法，失败的情况
     * @param statusCodeEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum){
        return new Result<>(statusCodeEnum.getCode(),statusCodeEnum.getMsg(),null,"");
    }

    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum, String description){
        return new Result<>(statusCodeEnum.getCode(),statusCodeEnum.getMsg(),null,description);
    }

    public static <T> Result<T> fail(int code,String msg, String description){
        return new Result<>(code,msg,null, description);
    }
}
