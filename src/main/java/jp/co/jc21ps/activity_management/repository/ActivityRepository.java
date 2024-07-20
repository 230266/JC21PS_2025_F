package jp.co.jc21ps.activity_management.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.jc21ps.activity_management.dto.ActivityDto;

@Repository
public class ActivityRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ActivityDto> findAll() {

        List<Map<String, Object>> activityList = jdbcTemplate.queryForList("""
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

        List<ActivityDto> activityDtoList = new ArrayList<ActivityDto>();
        for (Map<String, Object> activity : activityList) {
            activityDtoList.add(
                new ActivityDto(
                    (String) activity.get("activity_id"),
                    (String)activity.get("club_id"),
                    (String)activity.get("club_name"),
                    (String)activity.get("activity_name"),
                    (String)activity.get("activity_place"),
                    (LocalDateTime)activity.get("activity_start_time"),
                    (LocalDateTime)activity.get("activity_end_time"),
                    (String)activity.get("activity_description"),
                    (Integer)activity.get("max_participant")
                )
            );
        }   
        
        return activityDtoList;
    }
}
