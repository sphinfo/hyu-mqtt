package com.sph.hyu.mqtt.subscriber.roller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sph.hyu.mqtt.comm.LocalDateTimeDeserializer;
import com.sph.hyu.mqtt.subscriber.tracking.domain.TbIotItTrackingHist;
import com.sph.hyu.mqtt.subscriber.tracking.repository.TrackingHistoryJDBCRepository;
import com.sph.hyu.mqtt.subscriber.tracking.repository.TrackingHistoryRepository;

@Component
public class MqttRollerMessageSubscriber implements MessageHandler {
	
	private final ObjectMapper objectMapper;
	
	private final ModelMapper modelMapper;
	
	@Autowired
	private TrackingHistoryRepository trackingHistoryRepository;
	
	@Autowired
	private TrackingHistoryJDBCRepository trackingHistoryJDBCRepository;
	
	public MqttRollerMessageSubscriber () {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        
        modelMapper = new ModelMapper();
    }
	
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
    	if (message != null || message.getPayload() != null) {
    		String payload = (String) message.getPayload();
        	
        	TrackingRollerHistoryDto dto = null;
        	
        	TbIotItTrackingHist tbIotItTrackingHist = null;
        	
        	try {
        		// 사용자 정의 모듈 등록
                SimpleModule module = new SimpleModule();
                module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
                objectMapper.registerModule(module);
                
    			dto = objectMapper.readValue(payload, TrackingRollerHistoryDto.class);
    			
    			tbIotItTrackingHist = modelMapper.map(dto, TbIotItTrackingHist.class);
            	
            	String trackingId = trackingHistoryRepository.getTrackingId();
            	tbIotItTrackingHist.setTrackingId(trackingId);
            	
            	String eventDate = dto.getEventDt().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            	tbIotItTrackingHist.setEventDate(eventDate);
            	
            	LocalDateTime now = LocalDateTime.now();
            	tbIotItTrackingHist.setRegDt(now);
            	tbIotItTrackingHist.setRegId("MQTT");
            	
            	trackingHistoryRepository.addHist(tbIotItTrackingHist);
            	
            	trackingHistoryJDBCRepository.callMergeTrackingNow(
            			tbIotItTrackingHist.getPrjId(),
            			tbIotItTrackingHist.getAssetId(),
            			tbIotItTrackingHist.getUserId(),
            			tbIotItTrackingHist.getTrackingId()
        			);
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