package jp.co.jc21ps.activity_management.dto;

public class RegisterActivityDto {

    // 部署名
    private String clubName;

    // 部署ID
    private String clubId;

    public RegisterActivityDto() {

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
