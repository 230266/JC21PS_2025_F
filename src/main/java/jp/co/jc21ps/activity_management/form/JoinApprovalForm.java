package jp.co.jc21ps.activity_management.form;

public class JoinApprovalForm {

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // ユーザー名
    private String userName;

    // 部署名
    private String clubName;

    // リーダーフラグ
    private boolean leaderFlg;

    public JoinApprovalForm() {

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isLeaderFlg() {
        return leaderFlg;
    }

    public void setLeaderFlg(boolean leaderFlg) {
        this.leaderFlg = leaderFlg;
    }

}
