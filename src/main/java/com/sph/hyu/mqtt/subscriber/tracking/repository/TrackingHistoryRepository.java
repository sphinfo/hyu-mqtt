package com.sph.hyu.mqtt.subscriber.tracking.repository;

import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.sph.hyu.mqtt.subscriber.tracking.domain.TbIotItTrackingHist;

import reactor.core.publisher.Mono;

@Transactional
public interface TrackingHistoryRepository extends JpaRepository<TbIotItTrackingHist, Long>{
	
	final String TrackingId = ""
			+ "SELECT to_char(now(), 'YYYYMMDD') || lpad(CAST(nextval('iot.tracking_hist_id_seq') AS text), 8, '0')";
	@Query(value = TrackingId, nativeQuery = true)
	String getTrackingId();
	
	final String SaveHist = ""
			+ "INSERT INTO iot.tb_iot_it_tracking_hist ( "
			+ "  tracking_id, "
			+ "  prj_id, "
			+ "  user_id, "
			+ "  asset_id, "
			+ "  asset_nm, "
			+ "  gps_type, "
			+ "  geom, "
			+ "  mat_geom, "
			+ "  altitude, "
			+ "  heading, "
			+ "  speed, "
			+ "  asset_type, "
			+ "  event_dt,"
			+ "  event_date,"
			+ "  gps_dt,"
			+ "  reg_dt,"
			+ "  reg_id, "
			+ "  status, "
			+ "  intersection_status"
			+ ") values ("
			+ "  :#{#hist.trackingId}, "
			+ "  :#{#hist.prjId}, "
			+ "  :#{#hist.userId}, "
			+ "  :#{#hist.assetId}, "
			+ "  :#{#hist.assetNm}, "
			+ "  :#{#hist.gpsType}, "
			+ "  ST_SetSRID( "
			+ "     ST_GeomFromText( "
			+ "         'POINT(' || trunc(CAST(:#{#hist.lng} AS numeric), 12) || ' ' || trunc(CAST(:#{#hist.lat} AS numeric), 12) || ')'"
			+ "     ), 4326 "
			+ "  ), "
			+ "  ST_SetSRID( "
			+ "     ST_GeomFromText( "
			+ "         'POINT(' || trunc(CAST(:#{#hist.matLng} AS numeric), 12) || ' ' || trunc(CAST(:#{#hist.matLat} AS numeric), 12) || ')'"
			+ "     ), 4326 "
			+ "  ), "
			+ "  :#{#hist.altitude}, "
			+ "  :#{#hist.heading}, "
			+ "  :#{#hist.speed}, "
			+ "  :#{#hist.assetType}, "
			+ "  :#{#hist.eventDt}, "
			+ "  :#{#hist.eventDate}, "
			+ "  :#{#hist.gpsDt},"
			+ "  :#{#hist.regDt}, "
			+ "  :#{#hist.regId}, "
			+ "  :#{#hist.status}, "
			+ "  ("
			+ "    select scop.ST_Intersects( "
			+ "			scop.st_setsrid(scop.st_point( trunc(CAST(:#{#hist.lng} AS numeric),12), trunc(CAST(:#{#hist.lat} AS numeric),12) ),4326) "
			+ "		   ,scop.st_setsrid(scop.st_collect(array( "
			+ "				select CAST(scop.ST_AsText(scop.ST_Buffer(CAST(scop.ST_setSRID(tlmpwi.path_data, 4326) AS geography), CAST(tlmpwi.buffer_size/100 AS decimal), 'endcap=square join=round')) AS geometry) "
			+ "				  from scop.tb_li_mt_path_plan tlmpp "
			+ "					  ,scop.tb_li_it_plan_path tlipp "
			+ "					  ,scop.tb_li_mt_path_work_internal tlmpwi "
			+ "				 where 1 = 1 "
			+ "				   and tlmpp.plan_id = tlipp.plan_id "
			+ "				   and uuid(tlipp.path_id) = tlmpwi.path_id "
			+ "				   and tlmpwi.del_yn = 'N' "
			+ "				   and CAST(tlmpp.prj_id AS uuid) = :#{#hist.prjId} "
			+ "				   and CAST(tlmpp.asset_id AS uuid) = :#{#hist.assetId} "
			+ "				   and :#{#hist.eventDate} between tlmpp.work_start_date and tlmpp.work_end_date "
			+ "		    )), 4326) "
			+ "    ) "
			+ "  )"
			+ ") RETURNING tracking_id";			
			
	@Query(value = SaveHist, nativeQuery = true)
	public String addHist(@Param("hist") TbIotItTrackingHist hist);
}
