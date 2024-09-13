package jp.co.jc21ps.activity_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.ParticipantListEntity;

@Repository
public class ParticipantListRepository {
    private final JdbcTemplate jdbcTemplate;

    public ParticipantListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 画面表示
    public List<ParticipantListEntity> getParticipantListData(ParticipantListEntity paramEntity) {
        String sql = """
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

        List<Map<String, Object>> participantList = jdbcTemplate.queryForList(sql,
                paramEntity.getActivityId());

        List<ParticipantListEntity> responseListEntity = new ArrayList<>();

        // 空だった場合
        if (participantList.isEmpty()) {
            return responseListEntity;
        }

        for (Map<String, Object> participant : participantList) {
            ParticipantListEntity responseEntity = new ParticipantListEntity();

            // 活動ID
            responseEntity.setActivityId((String) participant.get("activity_id"));
            // ユーザーID
            responseEntity.setUserId((String) participant.get("user_id"));
            // 活動名
            responseEntity.setActivityName((String) participant.get("activity_name"));
            // ユーザー名
            responseEntity.setUserName((String) participant.get("user_name"));

            responseListEntity.add(responseEntity);
        }

        return responseListEntity;

    }

    public String getActivityName(ParticipantListEntity paramEntity) {
        String sql = """
                SELECT
                    activity_name
                FROM
                    trn_activity
                WHERE
                   activity_id = ?
                """;
        List<Map<String, Object>> actNameList = jdbcTemplate.queryForList(sql, paramEntity.getActivityId());

        if (actNameList.isEmpty()) {
            return "";
        }

        Map<String, Object> responseActName = actNameList.get(0);
        return ((String) responseActName.get("activity_name"));
    }
}
