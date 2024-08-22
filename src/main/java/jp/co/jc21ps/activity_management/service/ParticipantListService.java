package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.dto.ParticipantListDto;
import jp.co.jc21ps.activity_management.entity.ParticipantListEntity;
import jp.co.jc21ps.activity_management.repository.ParticipantListRepository;
import jp.co.jc21ps.dto.ParticipantDto;

@Service
public class ParticipantListService {
    private final ParticipantListRepository participantListRepository;

    public ParticipantListService(ParticipantListRepository participantListRepository){
        this.participantListRepository = participantListRepository;
    }

    //画面表示用
    public ParticipantDto getListData(ParticipantListDto participantListDto){
        ParticipantListEntity participantListEntity = new ParticipantListEntity();
        participantListEntity.setActivityId(participantListDto.getActivityId());
        participantListEntity.setUserId(participantListDto.getUserId());
        participantListEntity.setUserName(participantListDto.getUserName());

        String name = participantListRepository.getActivityName(participantListEntity);

        
        
        //
        List<ParticipantListEntity> lists = participantListRepository.getParticipantList(participantListEntity);

         //取ってきた値をdtoを介してcontrollerに投げる用
        List<ParticipantListDto> viewData = new ArrayList<>();
        ParticipantDto participantDto = new ParticipantDto();

        //ParticipantListDto型のに値を詰めている
        for(ParticipantListEntity entity : lists){
        ParticipantListDto dto = new ParticipantListDto();
        dto.setActivityId(entity.getActivityId());
        dto.setUserId(entity.getUserId());
        dto.setActivityName(entity.getActivityName());
        dto.setUserName(entity.getUserName());

        viewData.add(dto);
        }
        participantDto.setPariticipantDto(viewData);
        participantDto.setActivityName(name);

        

        return participantDto;
    }
}
