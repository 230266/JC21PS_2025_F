package jp.co.jc21ps.activity_management.dto;

public class ClubInfoRegisterDto {
    private String leaderClubId;
    private String clubName;
    private String clubDescription;

    public ClubInfoRegisterDto() {

    }

    public ClubInfoRegisterDto(String leaderClubId, String clubName, String clubDescription) {
        this.leaderClubId = leaderClubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
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
