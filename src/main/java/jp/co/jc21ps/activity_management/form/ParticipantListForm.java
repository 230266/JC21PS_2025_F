package jp.co.jc21ps.activity_management.form;

public class ParticipantListForm {

    // 活動ID
    private String activityId;

    // ユーザーID
    private String userId;

    // 活動名
    private String activityName;

    // ユーザー名
    private String userName;

    public ParticipantListForm() {

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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
