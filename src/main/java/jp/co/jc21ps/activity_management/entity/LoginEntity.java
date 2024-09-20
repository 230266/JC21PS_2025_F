package jp.co.jc21ps.activity_management.entity;

public class LoginEntity {

    // パスワード
    private String password;

    // ログイン名
    private String loginName;

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    public LoginEntity() {

    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

}
