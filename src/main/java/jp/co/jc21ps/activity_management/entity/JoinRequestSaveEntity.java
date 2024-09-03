package jp.co.jc21ps.activity_management.entity;

public class JoinRequestSaveEntity {
    //ユーザーID
    private String userId;

    //部署ID
    private String clubId;

    //deleteフラグ
    private boolean deleteFlg;

    public JoinRequestSaveEntity() {

    }

    // public JoinRequestSaveEntity(String userId, String clubId){
    //     this.userId = userId;
    //     this.clubId = clubId;
    // }

    //ユーザーID
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    //部署ID
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

    //deleteフラグ
    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public boolean getDeleteFlg() {
        return deleteFlg;
    }
}
