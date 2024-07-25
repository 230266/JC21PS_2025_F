package jp.co.jc21ps.activity_management.form;

public class LoginForm {
    private String password;
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
