package com.rockyshen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rockyshen
 * @date 2024/10/29 12:52
 * 本注解可以加到：参数上、方法上
 * 加到参数上：表示这个参数不允许记录到日志中，针对敏感信息脱敏：密码
 * 加到方法上，表示这个方法的返回值不允许记录到日志中
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.METHOD})
public @interface NoLogAnnotation {
}
