package jp.co.jc21ps.activity_management.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.Activity;

@Repository
public class ActivityRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Activity> findAll() {

        List<Map<String, Object>> results = jdbcTemplate.queryForList("""
                SELECT
                    ta.activity_id,
                    ta.club_id,
                    mc.club_name,
                    ta.activity_name,
                    ta.activity_place ,
                    ta.activity_start_time,
                    ta.activity_end_time,
                    ta.activity_description ,
                    ta.max_participant
                FROM
                    trn_activity ta
                JOIN mst_club mc 
                ON
                    ta.club_id = mc.club_id
                """);

        List<Activity> activityList = new ArrayList<Activity>();
        for (Map<String, Object> result : results) {
            activityList.add(
                new Activity(
                    (String) result.get("activity_id"),
                    (String) result.get("club_id"),
                    (String) result.get("club_name"),
                    (String) result.get("activity_name"),
                    (String) result.get("activity_place"),
                    (LocalDateTime) result.get("activity_start_time"),
                    (LocalDateTime) result.get("activity_end_time"),
                    (String) result.get("activity_description"),
                    (Integer) result.get("max_participant")
                )
            );
        }   
        
        return activityList;
    }
}
