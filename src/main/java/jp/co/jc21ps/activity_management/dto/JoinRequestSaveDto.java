package jp.co.jc21ps.activity_management.dto;

public class JoinRequestSaveDto {

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // deleteフラグ
    private boolean deleteFlg;

    public JoinRequestSaveDto() {

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

    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public boolean getDeleteFlg() {
        return deleteFlg;
    }
}
