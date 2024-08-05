package jp.co.jc21ps.activity_management.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.LoginEntity;
import java.util.List;
import java.util.Map;

import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.entity.TopSaveEntity;

@Repository
public class TopRepository {

    private final JdbcTemplate jdbcTemplate;

    public TopRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    
    //
    public TopSaveEntity getTopSave(TopSaveEntity topSaveEntity){
        String sqlSave = """
                SELECT * 
                FROM 
                    trn_participant
                WHERE
                    activity_id = ? 
                AND
                    user_id = ?; ";
                """;
        return topSaveEntity;        
    }

    //画面表示
    //部署ID、部活名、活動ID、活動名、活動場所、活動日、活動時間、活動説明、参加予定人数、参加上限人数、参加予定フラグ、過半数フラグ
    public List<TopEntity> getTop(TopEntity topEntity){
        String sql = """
                SELECT 
                    activity.*,
                    club.club_id,
                    club.club_name,
                    count.count,
                    isnull(participant.user_id) != 1 as participation_flg
                FROM 
                     trn_activity as activity
                INNER JOIN
                    mst_club as club USING(club_id) 
                INNER JOIN
                    trn_club_member as member ON club.club_id = member.club_id 
                LEFT JOIN 
                    (SELECT activity_id,count(*) as count FROM trn_participant GROUP BY activity_id) as count ON count.activity_id = activity.activity_id 
                LEFT JOIN
                    trn_participant as participant ON participant.user_id = ? 
                AND
                    participant.activity_id = activity.activity_id 
                WHERE 
                    member.user_id = ?  
                ORDER BY
                     club.club_id ASC,activity.activity_start_time ASC;
                """;
    
        //?の内容をかっこの中に書く
            List<Map<String,Object>> activityList = jdbcTemplate.queryForList(sql,topEntity.getUserId(),topEntity.getUserId());
            List<TopEntity> topEntities  = new ArrayList<>();
        
        //空だった場合
        if(activityList.isEmpty()){
            return topEntities;
        }




        TopEntity top = new TopEntity();
        for(Map<String, Object> activity : activityList){
            top.setClubId((String)activity.get("club_Id"));
            top.setClubName((String)activity.get("club_name"));
            top.setActivityId((String)activity.get("activity_id"));
            top.setActivityName((String)activity.get("activity_Name"));
            top.setActivityPlace((String)activity.get("activity_place"));
            top.setDispActivityDate((String)activity.get("activity_start_time"));
            top.setActivityStartTime((String)activity.get("activity_start_time"));
            top.setActivityEndTime((String)activity.get("activity_end_time"));
            top.setActivityDescription((String)activity.get("activity_description"));
            top.setParticipantsCount((String)activity.get("participantCount"));
            top.setMaxParticipant((String)activity.get("max_partcipant"));
            top.setIsParticipationFlg((boolean)activity.get("participation_flg"));
            top.setIsMajorityFlg((boolean)activity.get("isMajorityFlg"));
        
            //リストで返そう！
       
            
            topEntities.add(top);
        }
        return topEntities;
    }


}   


