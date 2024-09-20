package jp.co.jc21ps.activity_management.form;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class TopDataForm {

    @NotNull
    private String activityId;

    @NotNull
    private String userId;

    private String clubId;
    private String clubName;
    // 参加予定フラグ
    private boolean isParticipationFlg;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isParticipationFlg() {
        return isParticipationFlg;
    }

    public void setParticipationFlg(boolean isParticipationFlg) {
        this.isParticipationFlg = isParticipationFlg;
    }

}
