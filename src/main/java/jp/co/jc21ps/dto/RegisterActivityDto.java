package jp.co.jc21ps.dto;

public class RegisterActivityDto {

    private String clubName;
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
