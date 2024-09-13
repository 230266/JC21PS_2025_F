package jp.co.jc21ps.activity_management.dto;

public class RegisterActivityDto {
    private String clubName;
    private String clubId;

    public RegisterActivityDto() {

    }

    // public void RegisterActivityEntity(String clubName, String clubId) {
    // this.clubName = clubName;
    // this.clubId = clubId;
    // }
    // 部署名
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    // 部署ID
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }
}
