package jp.co.jc21ps.activity_management.entity;

import java.time.LocalDateTime;

public class Activity {
    private String activityId;
    private String clubId;
    private String clubName;
    private String activityName;
    private String activityPlace;
    private LocalDateTime activityStartTime;    
    private LocalDateTime activityEndTime;
    private String activityDescription;
    private Integer max_participant;

    public Activity(String activityId, String clubId, String clubName, String activityName, String activityPlace, LocalDateTime activityStartTime, LocalDateTime activityEndTime, String activityDescription, Integer max_participant) {
        this.activityId = activityId;
        this.clubId = clubId;
        this.clubName = clubName;
        this.activityName = activityName;
        this.activityPlace = activityPlace;
        this.activityStartTime = activityStartTime;
        this.activityEndTime = activityEndTime;
        this.activityDescription = activityDescription;
        this.max_participant = max_participant;
    }

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

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
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

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Integer getMax_participant() {
        return max_participant;
    }

    public void setMax_participant(Integer max_participant) {
        this.max_participant = max_participant;
    }
}
