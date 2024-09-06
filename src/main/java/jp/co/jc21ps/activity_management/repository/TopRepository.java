package jp.co.jc21ps.activity_management.repository;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.entity.TopDataEntity;

@Repository
public class TopRepository {

    private final JdbcTemplate jdbcTemplate;

    public TopRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 上限人数を取得
    public int getMaxParticipants(TopDataEntity topDataEntity) {
        String sqlcheck = """
                SELECT
                    max_participant
                FROM
                    trn_activity
                WHERE
                    activity_id = ?
                AND
                    club_id = ?
                """;

        Integer maxpar = jdbcTemplate.queryForObject(sqlcheck, Integer.class, topDataEntity.getActivityId(),
                topDataEntity.getClubId());

        return maxpar;
    }

    // 参加中の人数を取得
    public int isActivityParticipating(TopDataEntity topDataEntity) {
        String sqlCheck = """
                SELECT
                    COUNT(*)
                FROM
                    trn_participant
                WHERE
                    activity_id = ?
                AND
                    user_id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, topDataEntity.getActivityId(),
                topDataEntity.getUserId());
        return count;
    }

    // 参加ボタンを押したとき
    public void insertActivity(TopDataEntity topDataEntity) {
        String sqlInsert = """
                INSERT INTO
                    trn_participant
                VALUES (?,?)
                """;

        Object[] paramList = {
                topDataEntity.getActivityId(),
                topDataEntity.getUserId(),
        };

        jdbcTemplate.update(sqlInsert, paramList);
    }

    // 不参加ボタンを押したとき
    public void deleteActivity(TopDataEntity topDataEntity) {
        String sqlDelete = """
                DELETE FROM
                    trn_participant
                 WHERE
                    activity_id = ?
                 AND
                    user_id = ?
                """;

        Object[] paramList = {
                topDataEntity.getActivityId(),
                topDataEntity.getUserId(),
        };

        jdbcTemplate.update(sqlDelete, paramList);
    }

    // 画面表示
    // 部署ID、部活名、活動ID、活動名、活動場所、活動日、活動時間、活動説明、参加予定人数、参加上限人数、参加予定フラグ
    public List<TopEntity> getTop(TopEntity topEntity) {
        String sql = """
                SELECT
                    distinct activity.*,
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
        // テスト終わったら下の条件文入れて
        // AND activity.activity_start_time > now()

        List<Map<String, Object>> activityList = jdbcTemplate.queryForList(sql, topEntity.getUserId(),
                topEntity.getUserId());

        List<TopEntity> topEntities = new ArrayList<>();

        // 空だった場合
        if (activityList.isEmpty()) {
            return topEntities;
        }

        // 活動時間(startTimeにもendTimeにも使う)
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:MM");

        // 活動日
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 活動リストの番号振り分け用

        int index = 1; // 番号付与の初期値

        for (Map<String, Object> activity : activityList) {
            TopEntity top = new TopEntity();

            // 部署ID
            top.setClubId((String) activity.get("club_Id"));

            // 部署名
            top.setClubName((String) activity.get("club_name"));

            // 活動ID
            top.setActivityId((String) activity.get("activity_id"));

            // 活動名
            top.setActivityName((String) activity.get("activity_name"));

            // 活動場所
            top.setActivityPlace((String) activity.get("activity_place"));

            // 開始時間
            Object startTimeObj = activity.get("activity_start_time");
            if (startTimeObj instanceof LocalDateTime) {
                LocalDateTime startTime = (LocalDateTime) startTimeObj;
                top.setActivityStartTime(startTime.format(formatterTime));
            } else if (startTimeObj instanceof String) {
                LocalDateTime startTime = LocalDateTime.parse((String) startTimeObj, formatterTime);
                top.setActivityStartTime(startTime.format(formatterTime));
            }

            // 終了時間
            Object endTimeObj = activity.get("activity_end_time");
            if (endTimeObj instanceof LocalDateTime) {
                LocalDateTime endTime = (LocalDateTime) endTimeObj;
                top.setActivityEndTime(endTime.format(formatterTime));
            } else if (endTimeObj instanceof String) {
                LocalDateTime endTime = LocalDateTime.parse((String) endTimeObj, formatterTime);
                top.setActivityEndTime(endTime.format(formatterTime));
            }

            // 活動日
            Object DateObj = activity.get("activity_start_time");
            if (DateObj instanceof LocalDateTime) {
                LocalDateTime Date = (LocalDateTime) DateObj;
                top.setDispActivityDate(Date.format(formatterDate));
            } else if (DateObj instanceof String) {
                LocalDateTime Date = LocalDateTime.parse((String) DateObj, formatterDate);
                top.setDispActivityDate(Date.format(formatterDate));
            }

            // 活動説明
            top.setActivityDescription((String) activity.get("activity_description"));

            // 参加人数
            Object countObj = activity.get("count");
            if (countObj instanceof Long) {
                Long countLong = (Long) countObj;
                if (countLong >= Integer.MIN_VALUE && countLong <= Integer.MAX_VALUE) {
                    top.setParticipantsCount(countLong.intValue());
                } else {
                    throw new IllegalArgumentException("Count value out of range for int: " + countLong);
                }
            } else if (countObj instanceof Number) {
                Number countNumber = (Number) countObj;
                top.setParticipantsCount(countNumber.intValue());
            }

            // 上限人数
            Object maxParticipantObj = activity.get("max_participant");
            String maxParticipantString = "";
            if (maxParticipantObj instanceof Number) {
                Integer maxParticipant = ((Number) maxParticipantObj).intValue();
                maxParticipantString = Integer.toString(maxParticipant);
            }
            top.setMaxParticipant(maxParticipantString);

            Object participationFlgObj = activity.get("participation_flg");
            if (participationFlgObj instanceof Number) {
                int participationFlgInt = ((Number) participationFlgObj).intValue();
                top.setIsParticipationFlg(participationFlgInt == 1);
            } else {
                top.setIsParticipationFlg(false); // デフォルト値
            }

            // 番号を設定
            top.setNo(index++);

            topEntities.add(top);

        }

        return topEntities;
    }
}
