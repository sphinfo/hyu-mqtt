package com.sph.hyu.mqtt.subscriber.tracking.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sph.hyu.mqtt.subscriber.tracking.domain.TbIotItTrackingHist;

import reactor.core.publisher.Mono;

@Repository
public class TrackingHistoryJDBCRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public String callMergeTrackingNow(UUID prjId, UUID assetId, String userId, String trackingId) {
		String sql = "SELECT iot.fn_merge_tracking_now(CAST(? AS uuid), CAST(? AS uuid), CAST(? AS varchar), CAST(? AS varchar))";
		
	    return jdbcTemplate.queryForObject(sql, new Object[]{prjId, assetId, userId, trackingId}, String.class);
	}
}
