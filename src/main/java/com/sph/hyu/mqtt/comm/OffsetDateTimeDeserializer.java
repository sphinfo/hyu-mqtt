package com.sph.hyu.mqtt.comm;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 사용자 정의 OffsetDateTime Deserializer
 * @author bjan
 *
 */
public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String date = jp.getText();
        try {
            return OffsetDateTime.parse(date); // ISO 8601 형식으로 파싱 시도
        } catch (DateTimeParseException e) {
            throw new IOException("Failed to parse OffsetDateTime: " + date, e);
        }
    }

}
