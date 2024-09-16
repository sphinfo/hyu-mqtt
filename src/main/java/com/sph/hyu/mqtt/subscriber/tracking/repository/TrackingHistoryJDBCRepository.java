package com.sph.hyu.mqtt.subscriber.tracking.repository;

import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sph.hyu.mqtt.subscriber.tracking.domain.TbIotItTrackingHist;

import reactor.core.publisher.Mono;

@Repository
public class TrackingHistoryJDBCRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String callMergeTrackingNow(UUID prjId, UUID assetId, String userId, String trackingId) {
		String sql = "SELECT iot.fn_merge_tracking_now(CAST(? AS uuid), CAST(? AS uuid), CAST(? AS varchar), CAST(? AS varchar))";

	    return jdbcTemplate.queryForObject(sql, new Object[]{prjId, assetId, userId, trackingId}, String.class);
	    
	    /*
		jdbcTemplate.update("SELECT iot.fn_merge_tracking_now(CAST(? AS varchar), CAST(? AS varchar), CAST(? AS varchar), CAST(? AS varchar))",
	                         prjId, assetId, userId, trackingId);
*/
	}

	
	//@Query(value = "SELECT iot.fn_merge_tracking_now(CAST(:prjId AS varchar), CAST(:assetId AS varchar), CAST(:userId AS varchar), CAST(:trackingId AS varchar))", nativeQuery = true)
	//void mergeTrackingNow(@Param("prjId") String prjId, @Param("assetId") String assetId, @Param("userId") String userId, @Param("trackingId") String trackingId);

}
