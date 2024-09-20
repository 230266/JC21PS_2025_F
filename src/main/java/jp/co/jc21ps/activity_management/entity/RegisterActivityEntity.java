package jp.co.jc21ps.activity_management.entity;

public class RegisterActivityEntity {

    // 部署名
    private String clubName;

    // 部署ID
    private String clubId;

    public RegisterActivityEntity() {

    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

}
