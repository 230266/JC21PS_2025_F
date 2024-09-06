package jp.co.jc21ps.activity_management.form;

import java.util.List;


public class ParticipantForm {
    
    private List<ParticipantListForm> participantList;
    private String activityName;
    
    
    public List<ParticipantListForm> getParticipantList() {
        return participantList;
    }
    public void setParticipantList(List<ParticipantListForm> participantList) {
        this.participantList = participantList;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    



}
