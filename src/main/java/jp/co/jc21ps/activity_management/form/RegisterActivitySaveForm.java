package jp.co.jc21ps.activity_management.form;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterActivitySaveForm {
    
    private String activityId;

    //活動名
    @NotBlank(message = "{NotBlank}")                           //必須入力
    @Size(max = 30, message = "{Size}")                         //30文字以内
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{Pattern}")  //半角英数字
    private String activityName;
    
    //活動日                 
    @NotNull(message = "{NotNull}")                 //必須入力
    @DateTimeFormat(pattern = "{DateTimeFormat}")   //日付形式yyyy-MM-dd
    private String activityDate;

    //活動場所
    @NotBlank(message = "{NotBlank}")                          //必須入力
    @Size(max = 30, message = "{Size}")                        //30文字以内
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{Pattern}") //半角英数字
    private String activityPlace;

    //活動時間(自)
    @NotNull(message = "{NotNull}")                                                             //必須入力
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "{Pattern.activityStartTime}") //hh:mm形式
    private String activityStartTime;

    //活動時間(至)
    @NotNull(message = "{NotNull}")                                                             //必須入力
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "{Pattern.activityEndTime}")   //hh:mm形式
    private String activityEndTime;

    //時間の前後関係チェック
    @AssertTrue(message = "{AssertTrue}")
    public boolean isDateValid(){
        try {
            int activityEndTimeInt = Integer.parseInt(activityEndTime);
            int activityStartTimeInt = Integer.parseInt(activityStartTime);
            return activityEndTimeInt >= activityStartTimeInt;
        } catch (NumberFormatException e) {
            return false; // 数字形式でない場合、無効として扱う
        }
    }

    //活動説明
    @NotBlank(message = "{NotBlank}")                          //必須入力
    @Size(max = 400, message = "{Size}")                       //400文字以内
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{Pattern}") //半角英数字
    private String activityDescription;

    //募集人数    
    @NotBlank(message = "{NotBlank}")                                   //必須入力
    @Pattern(regexp = "^[0-9]*$", message = "{Pattern.maxParticipant}") //半角数字
    @Min(value = 1, message = "{Min}")                                  //1以上
    @Max(value = 100, message = "{Max}")                                //100以下
    private String maxParticipant;

    private String clubId;

    //デフォルトコンストラクタ
    public RegisterActivitySaveForm() {
            
    }

    //引数付き
    public RegisterActivitySaveForm(String activityId, String activityName, String activityDate, String activityPlace, String activityStartTime, String activityEndTime, String activityDescription, String maxParticipant, String clubId) {
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
