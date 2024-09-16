package com.sph.hyu.mqtt.subscriber.dozer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
public class MqttDozerMessageSubscriber implements MessageHandler {

	private final ObjectMapper objectMapper;
	
	private final ModelMapper modelMapper;
	
	private GeometryFactory geometryFactory = new GeometryFactory();
	
	@Autowired
	private TrackingHistoryRepository trackingHistoryRepository;
	
	@Autowired
	private TrackingHistoryJDBCRepository trackingHistoryJDBCRepository;
	
	public MqttDozerMessageSubscriber () {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        
        modelMapper = new ModelMapper();
    }
	
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
    	System.out.println("=============================");
        System.out.println(message);
        
        if (message != null || message.getPayload() != null) {
            String payload = (String) message.getPayload();
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            
            //MessageDto messageDto = new MessageDto(payload, topic);
            
            TrackingDozerHistoryDto dto = null;
            
            TbIotItTrackingHist tbIotItTrackingHist = null;
            
            try {
            	
            	// 사용자 정의 모듈 등록
                SimpleModule module = new SimpleModule();
                module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
                objectMapper.registerModule(module);
                
                
            	dto = objectMapper.readValue(payload, TrackingDozerHistoryDto.class);
            	
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
            //System.out.println(dto.toString());
            //processMessageDto(trackingHistoryDto, topic);
        }

    }
    
    private void processMessageDto(TrackingDozerHistoryDto trackingHistoryDto, String topic) {
        System.out.println("Processing message: " + trackingHistoryDto);
        System.out.println("Topic: " + topic);
    }
    
    public Point createPoint(double latitude, double longitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);  // JTS에서 좌표는 (x, y)로 사용
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);
        return point;
    }
    
    /*
     *     @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        // 메시지 페이로드와 토픽을 추출
        String payload = (String) message.getPayload();
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        
        // DTO로 변환
        MessageDto messageDto = new MessageDto(payload, topic);
        
        // DTO를 처리하는 로직을 추가
        processMessageDto(messageDto);
    }

    private void processMessageDto(MessageDto messageDto) {
        // DTO를 사용하여 필요한 로직을 구현
        System.out.println("Processing message: " + messageDto);
        // 예를 들어, 메시지를 데이터베이스에 저장하거나 다른 서비스로 전달할 수 있습니다.
    }
     */
}