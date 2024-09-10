package jp.co.jc21ps.activity_management.dto;

public class JoinRequestSaveDto {
    //ユーザーID
    private String userId;

    //部署ID
    private String clubId;

    //deleteフラグ
    private boolean deleteFlg;

    //デフォルト
    public JoinRequestSaveDto() {

    }

    //引数あり
    public JoinRequestSaveDto(String userId, String clubId, boolean deleteFlg) {
        this.userId = userId;
        this.clubId = clubId;
    }

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

    //deleteFlg
    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public boolean getDeleteFlg() {
        return deleteFlg;
    }
}
