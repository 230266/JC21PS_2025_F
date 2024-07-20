package jp.co.jc21ps.activity_management.repository;

import org.springframework.stereotype.Repository;

import jp.co.jc21ps.activity_management.entity.User;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getOne() {
        String sql = """
                SELECT
                    user_id,
                    login_name,
                    password
                FROM
                    mst_user
                LIMIT 1
                """;
        
        List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql);
        if (userList.size() == 0) {
            return new User("", "不明の方", "");
        } 
        
        
        Map<String, Object> user = userList.get(0);
        return new User(
            (String) user.get("user_id"),
            (String) user.get("login_name"),
            (String) user.get("password")
            );
    }
}
