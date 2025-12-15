package com.rockyshen.utils.luban;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;

/**
 * @author rockyshen
 * @date 2025/12/15 11:05
 */
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {
    public LocalTimeSerializer() {
    }

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localTime.toString());
    }
}

