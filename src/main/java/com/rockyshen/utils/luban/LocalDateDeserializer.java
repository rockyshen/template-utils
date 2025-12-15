package com.rockyshen.utils.luban;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author rockyshen
 * @date 2025/12/15 11:02
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    public LocalDateDeserializer() {
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String sourceString = jsonParser.getText();
        return EmptyUtil.isEmpty(sourceString) ? null : LocalDate.parse(sourceString);
    }
}
