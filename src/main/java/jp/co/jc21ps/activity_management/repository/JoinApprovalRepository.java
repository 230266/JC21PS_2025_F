package jp.co.jc21ps.activity_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.jc21ps.activity_management.entity.JoinApprovalDataEntity;
import jp.co.jc21ps.activity_management.entity.JoinApprovalEntity;

@Repository
public class JoinApprovalRepository {
    private final JdbcTemplate jdbcTemplate;

    public JoinApprovalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 画面表示
    public List<JoinApprovalEntity> getJoinApprovalList(JoinApprovalEntity paramEntity) {

        String sql = """
                SELECT
                    club.club_name,
                    club.club_id,
                    usr.user_id,
                    usr.user_name
                FROM
                    trn_join_request as request
                INNER JOIN
                    mst_user as usr
                ON
                    usr.user_id = request.user_id
                INNER JOIN
                    mst_club as club
                ON
                    request.club_id = club.club_id
                WHERE
                    request.club_id = ?
                """;

        List<Map<String, Object>> joinApprovalList = jdbcTemplate.queryForList(sql, paramEntity.getClubId());

        // JoinApprovalEnitity型のリスト、これにデータを詰めていく
        List<JoinApprovalEntity> responseEntity = new ArrayList<>();

        // 空だった場合
        if (joinApprovalList.isEmpty()) {
            return responseEntity;
        }

        for (Map<String, Object> joinApprovalLoop : joinApprovalList) {
            JoinApprovalEntity viewList = new JoinApprovalEntity();

            // 部署ID
            viewList.setClubId((String) joinApprovalLoop.get("club_Id"));

            // ユーザーID
            viewList.setUserId((String) joinApprovalLoop.get("user_id"));

            // ユーザー名
            viewList.setUserName((String) joinApprovalLoop.get("user_name"));

            // 部署名
            viewList.setClubName((String) joinApprovalLoop.get("club_name"));

            responseEntity.add(viewList);
        }
        return responseEntity;
    }

    // 部署名だけ取得する
    public String getClubName(JoinApprovalEntity paramEntity) {
        String sql = """
                          SELECT
                               club_name
                           FROM
                mst_club
                           WHERE
                club_id = ?
                           """;

        List<Map<String, Object>> clubNameList = jdbcTemplate.queryForList(sql, paramEntity.getClubId());

        if (clubNameList.isEmpty()) {
            return "";
        }

        Map<String, Object> responseEntity = clubNameList.get(0);
        return ((String) responseEntity.get("club_name"));
    }

    // insertする
    public void insertRequestInfo(JoinApprovalDataEntity paramEntity) {
        String sqlInsert = """
                INSERT INTO
                    trn_club_member
                VALUES
                    (?,?,?)
                """;

        Object[] paramList = {
                paramEntity.getClubId(),
                paramEntity.getUserId(),
                paramEntity.isLeaderFlg()
        };

        jdbcTemplate.update(sqlInsert, paramList);
    }

    // deleteする
    public void deleteRequestInfo(JoinApprovalDataEntity paramEntity) {
        String sqlDelete = """
                DELETE FROM
                    trn_join_request
                WHERE
                    club_id = ?
                AND
                    user_id = ?
                AND
                    leader_flg = ?
                """;
        Object[] paramList = {
                paramEntity.getClubId(),
                paramEntity.getUserId(),
                paramEntity.isLeaderFlg()
        };

        jdbcTemplate.update(sqlDelete, paramList);
    }

}
