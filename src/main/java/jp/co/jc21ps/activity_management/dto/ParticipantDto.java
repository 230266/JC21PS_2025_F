package jp.co.jc21ps.activity_management.dto;

import java.util.List;

public class ParticipantDto {
    private List<ParticipantListDto> pariticipantListDto;
    private String activityName;

    public ParticipantDto() {

    }

    public List<ParticipantListDto> getPariticipantListDto() {
        return pariticipantListDto;
    }

    public void setPariticipantListDto(List<ParticipantListDto> pariticipantListDto) {
        this.pariticipantListDto = pariticipantListDto;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

}
