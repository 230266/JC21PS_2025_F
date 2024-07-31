package jp.co.jc21ps.activity_management.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterActivityForm {
    //clubIdを受け取って、clubNameを返す

    //部署名
    private String clubName;

    //部署ID
    @NotNull
    private String clubId;

    public void RegisterActivityEntity(String clubName, String clubId) {
        this.clubName = clubName;
        this.clubId = clubId;
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
