package jp.co.jc21ps.activity_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.ParticipantListEntity;

@Repository
public class ParticipantListRepository {
    private final  JdbcTemplate jdbcTemplate;
    
    public ParticipantListRepository(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
    }

    //画面表示
    public List<ParticipantListEntity> getParticipantList(ParticipantListEntity participantListEntity){
        String sql= """
            SELECT 
                trn_participant.activity_id,
                mst_user.user_id,
                trn_activity.activity_name,
                mst_user.user_name
            FROM
                trn_participant
            JOIN
                mst_user
            ON
                trn_participant.user_id = mst_user.user_id
            JOIN 
                trn_activity
            ON 
                trn_participant.activity_id = trn_activity.activity_id
            WHERE 
                trn_activity.activity_id = ?
                """;
        
        //?の内容をかっこの中に書く
        List<Map<String,Object>> participantList = jdbcTemplate.queryForList(sql,participantListEntity.getActivityId());
        
        //ParticipantListEnitity型のリスト、これにデータを詰めていく
        List<ParticipantListEntity> participantListEntities  = new ArrayList<>();
        
        //空だった場合
        if(participantList.isEmpty()){
            return participantListEntities;
        }

        for(Map<String,Object> participant : participantList){
            ParticipantListEntity list = new ParticipantListEntity();
            
            //活動ID 
            list.setActivityId((String)participant.get("activity_id"));
            //ユーザーID
            list.setUserId((String)participant.get("user_id"));
            //活動名
            list.setActivityName((String)participant.get("activity_name"));
            //ユーザー名
            list.setUserName((String)participant.get("user_name"));

            participantListEntities.add(list);
        }

        return participantListEntities;

    }

    public String getActivityName(ParticipantListEntity participantListEntity){
        String sql = """
                SELECT
                    activity_name
                FROM
                    trn_activity
                WHERE
                   activity_id = ?  
                """;
        List<Map<String,Object>> actNameList = jdbcTemplate.queryForList(sql,participantListEntity.getActivityId());

        if (actNameList.isEmpty()) {
            return "";
        }

        Map<String,Object> actName = actNameList.get(0);
        return ((String)actName.get("activity_name"));
    }
}
