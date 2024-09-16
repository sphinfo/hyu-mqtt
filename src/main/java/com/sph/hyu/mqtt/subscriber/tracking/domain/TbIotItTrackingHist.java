package com.sph.hyu.mqtt.subscriber.tracking.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
@Table(name = "TB_IOT_IT_TRACKING_HIST", schema = "iot")
@Entity
public class TbIotItTrackingHist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SEQ")
	private Long seq;
	
	/**	Tracking 아이디 */
	@Column(name="TRACKING_ID", length=2, nullable=false)
	private String trackingId;
	
	/**	프로젝트 아이디 */
	@Column(name="PRJ_ID")
	//@Type(type = "org.hibernate.type.PostgresUUIDType")
	private UUID prjId;
	
	/**	사용자 아이디 */	
	@Column(name="USER_ID", length=50, nullable=false)
	private String userId;
	
	/**	차량번호 */
	//@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Column(name="ASSET_ID")
	private UUID assetId;
	
	/**	트럭명 ( 차량번호 입력 ) */
	@Column(name="ASSET_NM", length=36, nullable=false)
	private String assetNm ;

	/**	GPS 수신 타입
	 * 1. MOBILE : 핸드폰 GPS,
	 * 2. SINGLE : 외부 장비 싱글 GPS
	 * 3. DUAL : 외부 장비 듀얼 GPS)
	 * 4. MQTT : 자동화장비
	 * 6. IOT : 하위구동
	 *  */	
	@Column(name="GPS_TYPE", length=36, nullable=false)
	private String gpsType;
	
	/**	고도 */
	@Column(name="ALTITUDE", nullable=false)
	private float altitude;
	
	/**	방향 */
	@Column(name="HEADING", nullable=false)
	private float heading ;
	
	/**	속도 */
	@Column(name="speed", nullable=false)
	private float speed ;

	/**	차량종류(덤프, 굴삭기) */
	@Column(name="ASSET_TYPE", length=10, nullable=false)
	private String assetType ;
	
	/**	GPS 생성일자(모바일 전송시간) */
    @Column(name="EVENT_DT")
	private LocalDateTime eventDt;
	
	/**	GPS 생성일자(모바일 전송일자) */
    @Column(name="EVENT_DATE")
	private String eventDate;
    
    /**	GPS 생성일자 */
    @Column(name="GPS_DT")
	private LocalDateTime gpsDt;
	
	/**	TRIP 아이디 */
	@Column(name="TRIP_ID", length = 50)
	private String tripId;
	
	/**	경도 */
	@Transient
	private double lng;
	
	/**	위도 */
	@Transient
	private double lat;
	
	/**	경도 */
	@Transient
	private double matLng;
	
	/**	위도 */
	@Transient
	private double matLat;
	
	/**
	 * 상태
	 * 1:connected(정상동작)
	 * 2:stop(일정시간 위치데이터 못받아옴)
	 * 3:disconnected(연결안됨)
	 */
	@Column(name="status")
	private int status;
	
	/**	등록 일자 */
    @Column(name="REG_DT")
	private LocalDateTime regDt;
	
	/**	등록 아이디 */
	@Column(name="REG_ID", length = 20)
	private String regId;
	
	/**	변경 일자 */
    @Column(name="UPD_DT")
	private LocalDateTime updDt;	//변경 일자
	
	/**	변경 아이디 */
	@Column(name="UPD_ID")
	private String updId;
	
	@Column(name="ROLL")
	private double roll;
	
	@Column(name="PITCH")
	private double pitch;
}
