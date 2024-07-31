package jp.co.jc21ps.activity_management.dto;

public class TopDto {
    
    //ユーザーID
    private String userId;
    
    //NO
    private String no;
    
    //部活ID
    private String clubId;
    
    //部活名
    private String clubName;
    
    //活動ID
    private String activityId;
    
    //活動名
    private String activityName;
    
    //活動場所
    private String activityPlace;
    
    //活動日(表示用)
    private String dispActivityDate;
    
    //活動時間(表示用)
    private String dispActivityTime;

     //開始時間
     private String activityStartTime;

     //終了時間
     private String activityEndTime;
 

    //活動説明
    private String activityDescription;

    //参加予定人数
    private String participantsCount;

    // 参加上限人数
	private String maxParticipant;

	// 参加予定フラグ
	private String isParticipationFlg;

	// 過半数フラグ
	private String isMajorityFlg;

    

     
    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getDispActivityDate() {
        return dispActivityDate;
    }

    public void setDispActivityDate(String dispActivityDate) {
        this.dispActivityDate = dispActivityDate;
    }

    public String getDispActivityTime() {
        return dispActivityTime;
    }

    public void setDispActivityTime(String dispActivityTime) {
        this.dispActivityTime = dispActivityTime;
    }

    public String getActivityStartTime(){
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime){
        this.activityStartTime = activityStartTime;
    }

    public String getActivityEndTime(){
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime){
        this.activityEndTime = activityEndTime;
    }


    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(String participantsCount) {
        this.participantsCount = participantsCount;
    }

    public String getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(String maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public String getIsParticipationFlg() {
        return isParticipationFlg;
    }

    public void setIsParticipationFlg(String isParticipationFlg) {
        this.isParticipationFlg = isParticipationFlg;
    }

    public String getIsMajorityFlg() {
        return isMajorityFlg;
    }

    public void setIsMajorityFlg(String isMajorityFlg) {
        this.isMajorityFlg = isMajorityFlg;
    }

    
}
