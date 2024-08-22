package jp.co.jc21ps.activity_management.form;

public class JoinRequestSaveForm {
    //ユーザーID
    private String userId;

    //部署ID
    private String clubId;

    public String getUserId(String userId) {
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getClubId(String clubId) {
        return userId;
    }

    public void setClubId(String clubId){
        this.clubId = clubId;
    }
}
