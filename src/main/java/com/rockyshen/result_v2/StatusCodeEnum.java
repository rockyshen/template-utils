package com.rockyshen.result_v2;

import lombok.Getter;

/**
 * 状态码枚举类
 * @author rockyshen
 * @date 2024/9/10 13:48
 */

@Getter
public enum StatusCodeEnum {
    // 第四步：利用构造方法，构造枚举值
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NO_AUTH(40100,"无权限",""),
    NOT_LOGIN(40101,"未登录",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    // 第一步：定义枚举属性
    private final int code;
    // 状态码信息
    private final String msg;
    // 状态码详细描述
    private final String description;

    // 第二步：全参构造器
    StatusCodeEnum(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    // 第三步：getter方法
}
