package jp.co.jc21ps.activity_management.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginForm {

    // パスワード
    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

    // ログイン名
    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String loginName;

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    public LoginForm() {

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
