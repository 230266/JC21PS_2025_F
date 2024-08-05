package jp.co.jc21ps.activity_management.dto;

public class LoginDto {
    private String userId;
    private String clubId;
    private String loginName; 
    private String password;

    public LoginDto(String userId,String clubId, String loginName, String password) {
        this.userId = userId;
        this.clubId = clubId;
        this.loginName = loginName;
        this.password = password;
    } 

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setClubId(String clubId){
        this.clubId = clubId;
    }

    public String getClubId(){
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
