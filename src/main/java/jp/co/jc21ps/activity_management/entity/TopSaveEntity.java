package jp.co.jc21ps.activity_management.entity;


public class TopSaveEntity {
    private String activityId;
    private String userId;
    private String clubId;
    private String clubName;
 
    public TopSaveEntity(String activityId,String userId,String clubId, String clubName){
     this.activityId = activityId;
     this.userId = userId;
     this.clubId = clubId;
     this.clubName = clubName;
    }
 
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
 }