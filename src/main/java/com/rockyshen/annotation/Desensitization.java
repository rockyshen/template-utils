package com.rockyshen.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rockyshen.constant.DesensitizationStrategyEnum;
import com.rockyshen.utils.DesensitizationJsonSerializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rockyshen
 * @date 2026/1/8 23:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside    // 让 自定义注解 能够"继承"其上标注的所有 Jackson 注解。
@JsonSerialize(using = DesensitizationJsonSerializable.class)  // 指定使用哪个自定义序列化器来处理被标注的字段。
public @interface Desensitization {
    /**
     * 脱敏策略
     */
    DesensitizationStrategyEnum value();
}
