package jp.co.jc21ps.dto;

import java.util.List;

import jp.co.jc21ps.activity_management.dto.JoinApprovalDto;



public class JoinApprovalNameDto {
    
    private List<JoinApprovalDto> joinApprovalDto;
    private String clubName;

    
    public List<JoinApprovalDto> getJoinApprovalDto() {
        return joinApprovalDto;
    }
    public void setJoinApprovalDto(List<JoinApprovalDto> joinApprovalDto) {
        this.joinApprovalDto = joinApprovalDto;
    }
    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}    

    
   
