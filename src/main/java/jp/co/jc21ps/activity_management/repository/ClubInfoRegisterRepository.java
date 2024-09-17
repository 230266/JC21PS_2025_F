package jp.co.jc21ps.activity_management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;
import jp.co.jc21ps.activity_management.entity.ClubInfoRegisterEntity;

//DB接続クラス
@Repository
public class ClubInfoRegisterRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClubInfoRegisterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ClubInfoRegisterEntity getClubInfo(ClubInfoRegisterEntity paramEntity) {

        String sql = """
                SELECT
                    club_name, club_description
                FROM
                    mst_club
                WHERE
                    club_id = ?
                """;

        // DBから取得する
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, paramEntity.getLeaderClubId());
        ClubInfoRegisterEntity responseEntity = new ClubInfoRegisterEntity();
        responseEntity.setClubName((String) result.get("club_name"));
        responseEntity.setClubDescription((String) result.get("club_description"));
        return responseEntity;
    }

    // 入力された説明をアップデートする
    public void updateClubInfo(ClubInfoRegisterEntity paramEntity) {
        String sql = """
                UPDATE
                    mst_club
                SET
                    club_description = ?
                WHERE
                    club_id = ?
                """;

        // パラメータの設定
        Object[] paramList = {
                paramEntity.getClubDescription(),
                paramEntity.getLeaderClubId()
        };

        // DBでアップデート
        jdbcTemplate.update(sql, paramList);
    }

}
