package jp.co.jc21ps.activity_management.entity;

import java.time.LocalDateTime;

public class Activity {
    private String activityId;
    private String clubId;    
    private String activityName;
    private String activityPlace;
    private LocalDateTime activityStartTime;    
    private LocalDateTime activityEndTime;
    private Integer max_participant;

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getActivityId() {
        return activityId;
    }

    public String getClubId() {
        return clubId;
    }
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityPlace() {
        return activityPlace;
    }
    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }
    public LocalDateTime getActivityStartTime() {
        return activityStartTime;
    }
    public void setActivityStartTime(LocalDateTime activityStartTime) {
        this.activityStartTime = activityStartTime;
    }
    public LocalDateTime getActivityEndTime() {
        return activityEndTime;
    }
    public void setActivityEndTime(LocalDateTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
    public Integer getMax_participant() {
        return max_participant;
    }
    public void setMax_participant(Integer max_participant) {
        this.max_participant = max_participant;
    }
}
