package jp.co.jc21ps.activity_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
import jp.co.jc21ps.activity_management.entity.JoinRequestSaveEntity;

//Db接続クラス
@Repository
public class JoinRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public JoinRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 初期表示(ユーザーIDから部署名、部署説明を取得するメソッド)
    public List<JoinRequestEntity> getJoinRequestById(JoinRequestEntity paramEntity) {

        // 取得したデータをリストにしてエンティティに変換
        List<JoinRequestEntity> responseEntity = new ArrayList<>();

        String sql = """
                SELECT * FROM
                    mst_club
                WHERE
                    club_id NOT IN
                        (SELECT club_id
                         FROM trn_join_request
                         WHERE user_id = ?)
                    AND
                    club_id NOT IN
                        (SELECT club_id
                         FROM trn_club_member
                         WHERE user_id = ?)
                """;
        // DBから取得する
        List<Map<String, Object>> joinRequestList = jdbcTemplate.queryForList(sql, paramEntity.getUserId(),
                paramEntity.getUserId());

        // 空だった場合
        if (joinRequestList.isEmpty()) {
            return responseEntity;
        }

        // リストを回す
        for (Map<String, Object> joinRequest : joinRequestList) {
            JoinRequestEntity joinData = new JoinRequestEntity();

            // エンティティに部署IDと部署説明をセットする
            // 部署名
            joinData.setClubName((String) joinRequest.get("club_name"));
            // 部署説明
            joinData.setClubDescription((String) joinRequest.get("club_description"));
            // clubId
            joinData.setClubId((String) joinRequest.get("club_id"));

            responseEntity.add(joinData);

        }
        return responseEntity;
    }

    // 申請処理
    public void insertClub(JoinRequestSaveEntity paramEntity) {
        String sql = """
                INSERT INTO
                    trn_join_request (user_Id,
                                      club_Id,
                                      leader_flg
                                      )
                VALUES (?, ?, false)
                """;
        // ここまでIdを持ってくる

        // パラメータの設定
        Object[] paramList = {
                paramEntity.getUserId(),
                paramEntity.getClubId(),
        };

        // DBに挿入
        jdbcTemplate.update(sql, paramList);
    }
}