package jp.co.jc21ps.activity_management.repository;

import org.springframework.stereotype.Repository;
import jp.co.jc21ps.activity_management.entity.LoginEntity;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LoginEntity getLoginData(LoginEntity paramEntity) {
        String sql = """
                SELECT
                 user.user_id,
                 member.club_id,
                 user.login_name
                FROM
                 mst_user user
                LEFT JOIN
                 trn_club_member member
                ON
                 user.user_id = member.user_id
                AND
                 member.leader_flg = 1
                WHERE
                 user.login_name = ?
                 AND
                  user.password =  ? ;
                """;

        List<Map<String, Object>> loginList = jdbcTemplate.queryForList(sql, paramEntity.getLoginName(),
                paramEntity.getPassword());

        if (loginList.size() == 0) {
            LoginEntity responseEntity = new LoginEntity();
            responseEntity.setPassword("");
            responseEntity.setLoginName("");
            responseEntity.setUserId(null);
            responseEntity.setClubId(null);
            return responseEntity;
        }

        Map<String, Object> login = loginList.get(0);
        LoginEntity responseEntity = new LoginEntity();
        responseEntity.setClubId((String) login.get("club_id"));
        responseEntity.setUserId((String) login.get("user_id"));
        responseEntity.setLoginName((String) login.get("login_name"));
        responseEntity.setPassword(null);
        return responseEntity;
    }
}
