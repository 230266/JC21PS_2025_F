package jp.co.jc21ps.activity_management.entity;
import java.time.LocalDateTime;

//入力値のエンティティ
public class RegisterActivitySaveEntity {

    //活動ID
    private String activityId;
    
    //活動名
    private String activityName;

    //活動場所
    private String activityPlace;

    //活動時間(自)
    private LocalDateTime activityStartTime;

    //活動時間(至)
    private LocalDateTime activityEndTime;

    //活動説明
    private String activityDescription;

    //募集人数
    private int maxParticipant;

    //部署ID
    private String clubId;

    //デフォルトコンストラクタ
    public RegisterActivitySaveEntity() {
        
    }

    public RegisterActivitySaveEntity(String activityId, String activityName, String activityPlace, LocalDateTime activityStartTime, LocalDateTime activityEndTime, String activityDescription, int maxParticipant, String clubId) {
        this.activityId = activityId;
        this.activityName = activityName;
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

    //活動場所
    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    //活動時間(自)
    public void setActivityStartTime(LocalDateTime activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public LocalDateTime getActivityStartTime() {
        return activityStartTime;
    }

    //活動時間(至)
    public void setActivityEndTime(LocalDateTime activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public LocalDateTime getActivityEndTime() {
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
    public void setMaxParticipant(int MaxParticipant) {
        this.maxParticipant = maxParticipant ;
    }

    public int getMaxParticipant() {
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
