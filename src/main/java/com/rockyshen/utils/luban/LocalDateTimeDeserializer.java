package com.rockyshen.utils.luban;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author rockyshen
 * @date 2025/12/15 11:07
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    public LocalDateTimeDeserializer() {
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String sourceString = jsonParser.getText();
        return EmptyUtil.isEmpty(sourceString) ? null : LocalDateTime.parse(sourceString);
    }
}
