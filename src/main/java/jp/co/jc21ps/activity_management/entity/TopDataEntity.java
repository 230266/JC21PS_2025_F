package jp.co.jc21ps.activity_management.entity;

public class TopDataEntity {

    // 活動ID
    private Integer activityId;

    // ユーザーID
    private String userId;

    // 部署ID
    private String clubId;

    // 部署名
    private String clubName;

    public TopDataEntity() {

    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
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