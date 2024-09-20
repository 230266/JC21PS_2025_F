package jp.co.jc21ps.activity_management.dto;

public class LoginDto {

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // ログイン名
    private String loginName;

    // パスワード
    private String password;

    public LoginDto() {

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
