package jp.co.jc21ps.activity_management.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginForm {
    //チェックする変数に対してアノテーションをつける
    @NotBlank(message = "パスワードは必須です。")
    @Size(max = 30, message = "パスワードは最大３０文字までです。")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数字で入力してください。")
    private String password;
    
    @NotBlank(message = "ログインネームは必須です。")
    @Size(max = 30, message = "ログインネームは最大３０文字までです。")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ログインネームは半角英数字で入力してください。")
    private String loginName;
    
    
    private String userId;
    private String clubId;

    public LoginForm(String loginName,String password, String userId,String clubId){
        this.loginName = loginName;
        this.password = password;
        this.userId = userId;
        this.clubId = clubId;

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
