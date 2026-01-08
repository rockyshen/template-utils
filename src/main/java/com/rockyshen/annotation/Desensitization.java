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
/**
 * @JacksonAnnotationsInside 是 Jackson 库中一个非常有用的注解，它允许我们创建包含多个 Jackson 注解的自定义注解。
 * 这使得我们在使用这些自定义注解时，可以像使用单个注解一样方便地应用多个序列化/反序列化规则
 * 即该自定义注解可以包含多个 Jackson 提供的注解，并在被应用到类、方法或字段上时，这些包含的注解会被内联应用到目标元素上
 */
@JacksonAnnotationsInside
/**
 * @JsonSerialize 是 Jackson 库中的一个注解，用于指定在将 Java 对象序列化为 JSON 时使用的自定义序列化器。
 * 通过该注解，你可以为特定的类、字段或 getter 方法指定一个自定义的序列化逻辑，
 * 从而控制对象在序列化为 JSON 时的表现形式。
 */
@JsonSerialize(using = DesensitizationJsonSerializable.class)
public @interface Desensitization {
    /**
     * 脱敏策略
     *
     * @return
     */
    DesensitizationStrategyEnum value();
}
