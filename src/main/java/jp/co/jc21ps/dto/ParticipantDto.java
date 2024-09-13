package jp.co.jc21ps.dto;

import java.util.List;

import jp.co.jc21ps.activity_management.dto.ParticipantListDto;

public class ParticipantDto {
    private List<ParticipantListDto> pariticipantListDto;
    private String activityName;

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
