package com.sph.hyu.mqtt.comm;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * 사용자 정의 LocalDateTime Deserializer
 * @author bjan
 *
 */
public class LocalDateTimeDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter customFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String date = jp.getText();

        // 문자열이 ISO 8601 형식인지 확인
        try {
        	// OffsetDateTime으로 파싱 후 LocalDateTime으로 변환
            return OffsetDateTime.parse(date).toLocalDateTime();
        } catch (DateTimeParseException e) {
        	// ISO 8601 형식이 아닌 경우 사용자 정의 형식으로 파싱 시도
            return LocalDateTime.parse(date, customFormatter);
        }
    }
}
