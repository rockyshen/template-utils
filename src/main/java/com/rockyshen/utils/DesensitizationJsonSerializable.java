package com.rockyshen.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.rockyshen.annotation.Desensitization;
import com.rockyshen.constant.DesensitizationStrategyEnum;

import java.io.IOException;
import java.util.Objects;

/**
 * 这个自定义的Jackson序列化器，必须是JsonSerializer的子类，这样Jackson才认
 * @author rockyshen
 * @date 2026/1/8 23:05
 */
public class DesensitizationJsonSerializable extends JsonSerializer<String> implements ContextualSerializer {
    // 基于枚举项的脱敏策略
    private DesensitizationStrategyEnum desensitizationStrategy;

    /**
     * 实现自：【接口】ContextualSerializer
     * 作用是：在序列化之前被调用，用于根据字段的上下文信息（如注解）创建定制化的序列化器。
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException
    {
        // 获取属性上的 @Desensitization 注解
        Desensitization annotation = property.getAnnotation(Desensitization.class);

        // 注解不为空 && 属性类型必须是字符串类型
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass()))
        {
            //设置脱敏策略
            this.desensitizationStrategy = annotation.value();
            return this;
        }
        // 返回默认的序列化器
        return prov.findValueSerializer(property.getType(), property);
    }

    /**
     * 重写自： 【抽象类】JsonSerializer
     * 执行序列化
     */
    @Override
    public void serialize(String s, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        // 使用脱敏策略将字符串处理后序列化到json中
        gen.writeString(desensitizationStrategy.getDesensitization().apply(s));
    }
}
