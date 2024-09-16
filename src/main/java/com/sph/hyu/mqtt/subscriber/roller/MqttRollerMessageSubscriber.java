package com.sph.hyu.mqtt.subscriber.roller;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MqttRollerMessageSubscriber implements MessageHandler {
	
	private final ObjectMapper objectMapper;
	
	public MqttRollerMessageSubscriber () {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
	
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
    	if (message != null || message.getPayload() != null) {
    		String payload = (String) message.getPayload();
        	
        	TrackingRollerHistoryDto dto = null;
        	
        	try {
    			dto = objectMapper.readValue(payload, TrackingRollerHistoryDto.class);
    		} catch (JsonMappingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (JsonProcessingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
}