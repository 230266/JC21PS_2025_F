package jp.co.jc21ps.dto;

public class RegisterActivitySaveDto {

    // 活動ID
    private String activityId;

    // 活動名
    private String activityName;

    // 活動日
    private String activityDate;

    // 活動場所
    private String activityPlace;

    // 活動時間（自）
    private String activityStartTime;

    // 活動時間（至）
    private String activityEndTime;

    // 活動説明
    private String activityDescription;

    // 募集人数
    private String maxParticipant;

    // クラブID
    private String clubId;

    public RegisterActivitySaveDto() {

    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setMaxParticipant(String maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public String getMaxParticipant() {
        return maxParticipant;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

}