package com.rockyshen.utils.luban;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;

/**
 * @author rockyshen
 * @date 2025/12/15 11:05
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    public LocalTimeDeserializer() {
    }

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String sourceString = jsonParser.getText();
        return EmptyUtil.isEmpty(sourceString) ? null : LocalTime.parse(sourceString);
    }
}
