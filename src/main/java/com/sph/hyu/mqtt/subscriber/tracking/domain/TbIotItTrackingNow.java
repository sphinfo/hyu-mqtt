package com.sph.hyu.mqtt.subscriber.tracking.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.locationtech.jts.geom.Geometry;
import lombok.Data;

@Data
@Table(name = "iot.tb_iot_it_tracking_now", schema = "iot")
@Entity
public class TbIotItTrackingNow {
	@Id
	private int seq;
	private String trackingId;
	private UUID prjId;
	private String userId;
	private UUID assetId;
	private String assetNm;
	private String gpsType;			//GPS 수신 타입 (MOBILE : 핸드폰 GPS, SINGLE : 외부 장비 싱글 GPS, DUAL : 외부 장비 듀얼 GPS)
	private Geometry geom;
	private float altitude;
	private float direction;
	private float speed;
	private float totalTrackingTime;
	private float idleTime;
	private float totalIdleTime;
	private double totalDistance;
	
	private String workType;
	private String assetType;
	private String materialType;
	private String assetStatus;
	
	private String pathId;
	private String destinationId;
	
	private String eventType;
	private LocalDateTime eventDt;
	private String tripGrpId;
	private String tripId;
	private String eventDate; // 생성일자
	
	@Transient
	private int cnt;
	private Date regDt;
	private String regId;
	private Date updDt;
	private String updId;

	private int status;	//1:connected(정상동작) 2:stop(일정시간 위치데이터 못받아옴) 3:disconnected(연결안됨)
}
