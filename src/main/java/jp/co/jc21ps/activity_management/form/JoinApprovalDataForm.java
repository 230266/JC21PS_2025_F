package jp.co.jc21ps.activity_management.form;

public class JoinApprovalDataForm {
    private String userId;
    private String clubId;
    private boolean leaderFlg;
    
    
    
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
    public boolean isLeaderFlg() {
        return leaderFlg;
    }
    public void setLeaderFlg(boolean leaderFlg) {
        this.leaderFlg = leaderFlg;
    }

    
}
