package jp.co.jc21ps.activity_management.form;

import jakarta.validation.constraints.NotNull;

public class RegisterActivityForm {

    // 部署名
    private String clubName;

    // 部署ID
    @NotNull
    private String clubId;

    public RegisterActivityForm() {

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
