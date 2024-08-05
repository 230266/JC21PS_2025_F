package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.repository.TopRepository;

@Service
public class TopService {
    
    private final TopRepository TopRepository;

    public TopService(TopRepository TopRepository){
        this.TopRepository = TopRepository;
    }

    //
    public List<TopDto> getTopData(TopDto topDto){
        TopEntity topEntity = new TopEntity();
        topEntity.setUserId(topDto.getUserId());

        List<TopEntity> tops = TopRepository.getTop(topEntity);
        
        //取ってきた値をdtoを介してcontrollerに投げる用
        List<TopDto> topDto2 = new ArrayList<>();
        TopDto Dto = new TopDto();
        //TopEntityからTopDtoに変換し、リストに追加
        for(TopEntity entity : tops){
            Dto.setNo(entity.getNo());
            Dto.setClubId(entity.getClubId());
            Dto.setClubName(entity.getClubName());
            Dto.setActivityId(entity.getActivityId());
            Dto.setActivityName(entity.getActivityName());
            Dto.setActivityPlace(entity.getActivityPlace());
            Dto.setDispActivityDate(entity.getDispActivityDate());
            Dto.setDispActivityTime(entity.getDispActivityTime());
            Dto.setActivityStartTime(entity.getActivityStartTime());
            Dto.setActivityEndTime(entity.getActivityEndTime());
            Dto.setActivityDescription(entity.getActivityDescription());
            Dto.setParticipantsCount(entity.getParticipantsCount());
            Dto.setMaxParticipant(entity.getMaxParticipant());
            //dtoに受け渡すため、boolean型をStringに変える➡「～.toString()」
            Dto.setIsParticipationFlg(Boolean.toString(entity.getIsParticipationFlg()));
            Dto.setIsMajorityFlg(Boolean.toString(entity.getIsMajorityFlg()));
        
            topDto2.add(Dto);
        }
         
        return topDto2;
    }
} 