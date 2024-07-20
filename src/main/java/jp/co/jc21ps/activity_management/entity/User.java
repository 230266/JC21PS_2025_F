package jp.co.jc21ps.activity_management.entity;

public class User {
    private String userId;
    private String loginName;
    private String password;

    public User(String userId, String loginName, String password) {
        this.userId = userId;
        this.loginName = loginName;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setloginName(String loginName) {
        this.loginName = loginName;
    }

    public String getloginName() {
        return loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
