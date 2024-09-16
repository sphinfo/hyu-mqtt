package com.sph.hyu.mqtt.subscriber.grader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingGraderHistoryDto {

	//private long seq;
	private String trackingId ;		//Tracking 아이디
	private String prjId ;			//프로젝트 아이디
	private String userId ;			//사용자 아이디
	private String assetId ;		//차량번호
	private String assetNm ;		//트럭명 ( 차량번호 입력 )
	private String gpsType = "MQTT";			//GPS 수신 타입 (MOBILE : 핸드폰 GPS, SINGLE : 외부 장비 싱글 GPS, DUAL : 외부 장비 듀얼 GPS)
	@JsonProperty("grd_bdy_lttd")
	private double lng ;			//경도
	@JsonProperty("grd_bdy_lgtd")
	private double lat ;			//위도
	private double matLng ;			//경도
	private double matLat ;			//위도
	@JsonProperty("grd_bdy_attd")
	private float altitude ;		//고도
	private float externalAltitude;			// 외부장비 고도(지오이드 보정값이 적용되지 않은 고도값)
	private float externalAltitudeCorrection;	// 외부장비 고도 보정(지오이드 보정값)
	private float direction ;		//방향
	@JsonProperty("grd_bdy_z_pose")
	private float heading;
	@JsonProperty("grd_bdy_velocity_vel")
	private float speed ;			//속도
	private String workType ;		//업무 종류(상차/하차)
	private String assetType="08";	//차량종류(덤프, 굴삭기)
	private String materialType ;	//재료종류
	private String pathId ;			//순환경로 아이디
	private String destinationId ;	//순환경로 목적지 아이디
	@JsonProperty("Timestamp")
	private String eventDt ;		//모바일 GPS 전송 시간
	@JsonProperty("TimeStamp")
	private String gpsDt ;			//GPS 생성시간
	private int status=1;	//1:connected(정상동작) 2:stop(일정시간 위치데이터 못받아옴) 3:disconnected(연결안됨)
	@JsonProperty("grd_bdy_x_pose")
	private String roll;
	@JsonProperty("grd_bdy_y_pose")
	private String pitch;

	/*
	 @Override
	    public String toString() {
	        return "EquipmentDto{" +
	                "id='" + id + '\'' +
	                ", name='" + name + '\'' +
	                ", status='" + status + '\'' +
	                '}';
	    }
	    */
}
