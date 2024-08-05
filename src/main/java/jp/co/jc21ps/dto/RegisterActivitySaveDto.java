package jp.co.jc21ps.dto;

public class RegisterActivitySaveDto {

    //活動ID
    private String activityId;

    //活動名
    private String activityName;

    //活動日

    private String activityDate;

    //活動場所
    private String activityPlace;

    //開始時間
    private String activityStartTime;

    //終了時間
    private String activityEndTime;

    //活動説明
    private String activityDescription;

    //募集人数
    private String maxParticipant;

    //部署ID
    private String clubId;

    //デフォルトコンストラクタ
    public RegisterActivitySaveDto() {
            
    }
    public RegisterActivitySaveDto(String activityId, String activityName, String activityDate, String activityPlace, String activityStartTime, String activityEndTime, String activityDescription, String maxParticipant, String clubId) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityPlace = activityPlace;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
        this.activityDescription = activityDescription;
        this.maxParticipant = maxParticipant;
        this.clubId = clubId;
    }

    //活動ID
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    //活動名
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    //活動日
    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDate() {
        return activityDate;
    }


    //活動場所
    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    //活動時間(自)
    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    //活動時間(至)
    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    //活動説明
    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    //募集人数
    public void setMaxParticipant(String MaxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public String getMaxParticipant() {
        return maxParticipant;
    }

    //部署ID
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    public String getClubId() {
        return clubId;
    }
}