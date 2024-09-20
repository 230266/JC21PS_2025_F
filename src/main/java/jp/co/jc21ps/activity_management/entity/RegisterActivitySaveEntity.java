package jp.co.jc21ps.activity_management.entity;

import java.time.LocalDateTime;

public class RegisterActivitySaveEntity {

    // 活動ID
    private String activityId;

    // 活動名
    private String activityName;

    // 活動場所
    private String activityPlace;

    // 活動時間（自）
    private LocalDateTime activityStartTime;

    // 活動時間（至）
    private LocalDateTime activityEndTime;

    // 活動説明
    private String activityDescription;

    // 募集人数
    private int maxParticipant;

    // 部署ID
    private String clubId;

    public RegisterActivitySaveEntity() {

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

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityStartTime(LocalDateTime activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public LocalDateTime getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityEndTime(LocalDateTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public LocalDateTime getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

}
