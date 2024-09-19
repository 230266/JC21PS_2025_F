package jp.co.jc21ps.activity_management.dto;

public class SessionDto {

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // ユーザー名
    private String userName;

    public SessionDto() {

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
}
