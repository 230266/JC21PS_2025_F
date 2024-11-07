package jp.co.jc21ps.activity_management.repository;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;

@Repository
public class RegisterActivityRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String ACTIVITY_ID_PREFIX = "A";

    public RegisterActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 初期画面表示
    public RegisterActivityEntity getActivityByClubId(RegisterActivityEntity paramEntity) {

        String sql = """
                SELECT
                    club_name
                FROM
                    mst_club
                WHERE
                    club_id = ?
                """;

        Map<String, Object> getResult = jdbcTemplate.queryForMap(sql, paramEntity.getClubId());

        // responseEntityに値をセット
        RegisterActivityEntity responseEntity = new RegisterActivityEntity();
        responseEntity.setClubName((String) getResult.get("club_name"));

        return responseEntity;
    }

    // 入力値を登録
    public void saveActivity(RegisterActivitySaveEntity paramEntity) {

        String sql = """
                INSERT INTO
                    trn_activity (club_id,
                                  activity_name,
                                  activity_place,
                                  activity_start_time,
                                  activity_end_time,
                                  activity_description,
                                  max_participant)
                VALUES (?,?,?,?,?,?,?)
                """;

        // paramEntityから値をゲット
        Object[] paramList = {
                paramEntity.getClubId(),
                paramEntity.getActivityName(),
                paramEntity.getActivityPlace(),
                paramEntity.getActivityStartTime(), // LocalDateTime型
                paramEntity.getActivityEndTime(), // LocalDateTime型
                paramEntity.getActivityDescription(),
                paramEntity.getMaxParticipant(), // int型
        };

        jdbcTemplate.update(sql, paramList);
    }

    // シーケンスからactivityIdを取得
    public String getNextActivityId() throws Exception {

        String sql = "SELECT nextval('activity_id_sequence') AS id";

        try {
            Integer sequenceValue = jdbcTemplate.queryForObject(sql, Integer.class);

            if (sequenceValue != null) {
                // フォーマット指定
                return ACTIVITY_ID_PREFIX + String.format("%07d", sequenceValue);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            // DB実行失敗した場合
            e.printStackTrace();
            throw new Exception();
        }

    }

}
