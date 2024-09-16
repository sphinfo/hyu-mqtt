package com.sph.hyu.mqtt.subscriber;

public class MessageDto {
    private String payload;
    private String topic;

    public MessageDto(String payload, String topic) {
        this.payload = payload;
        this.topic = topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "payload='" + payload + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}