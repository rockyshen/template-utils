package com.rockyshen.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果类
 * @author rockyshen
 * @date 2024/9/10 13:19
 */

@Data
@NoArgsConstructor   // 无参构造
@AllArgsConstructor  // 全参构造
public class Result<T> {
    private int code;
    private String message;
    private T data;
    // 更详细的描述，用于全局异常处理器接收的详细描述
    private String description;


    // 提供各种参数组合的构造函数就行了,不够用了，回来继续加
    // 从传入的参数中提取：满足Result类需要的四个属性的就行了！

    // 1.全参构造器 @AllArgsConstructor
    // 2.无参构造器 @NoArgsConstructor

    /**
     * 只传状态码枚举类对象
     * @param statusCodeEnum
     */
    public Result(StatusCodeEnum statusCodeEnum){
        this(statusCodeEnum.getCode(),statusCodeEnum.getMessage(),null,"");
    }

    /**
     * 传状态码枚举类对象 和 data
     * @param statusCodeEnum  状态码枚举类对象
     * @param data            具体数据
     */
    public Result(StatusCodeEnum statusCodeEnum,T data){
        this(statusCodeEnum.getCode(),statusCodeEnum.getMessage(),data,"");
    }

    /**
     *
     * @param statusCodeEnum   状态码枚举类对象
     * @param data             具体数据
     * @param description      详细描述
     */
    public Result(StatusCodeEnum statusCodeEnum,T data,String description){
        this(statusCodeEnum.getCode(),statusCodeEnum.getMessage(),data,description);
    }

}
