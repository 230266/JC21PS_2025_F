package jp.co.jc21ps.activity_management.dto;

public class ClubInfoRegisterDto {

    // leaderClubId
    private String leaderClubId;

    // 部署名
    private String clubName;

    // 部署説明
    private String clubDescription;

    public ClubInfoRegisterDto() {

    }

    public String getLeaderClubId() {
        return leaderClubId;
    }

    public void setLeaderClubId(String leaderClubId) {
        this.leaderClubId = leaderClubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
}
