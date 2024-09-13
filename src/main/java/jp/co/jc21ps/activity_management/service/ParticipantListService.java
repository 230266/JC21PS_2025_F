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

    public ParticipantListService(ParticipantListRepository participantListRepository) {
        this.participantListRepository = participantListRepository;
    }

    // 画面表示用
    public ParticipantDto getParticipantListData(ParticipantListDto paramDto) {
        ParticipantListEntity participantListEntity = new ParticipantListEntity();
        participantListEntity.setActivityId(paramDto.getActivityId());
        participantListEntity.setUserId(paramDto.getUserId());
        participantListEntity.setUserName(paramDto.getUserName());

        String responseActName = participantListRepository.getActivityName(participantListEntity);

        List<ParticipantListEntity> participantList = participantListRepository
                .getParticipantListData(participantListEntity);

        List<ParticipantListDto> responseListDto = new ArrayList<>();
        ParticipantDto responseDto = new ParticipantDto();

        for (ParticipantListEntity entity : participantList) {
            ParticipantListDto setDto = new ParticipantListDto();
            setDto.setActivityId(entity.getActivityId());
            setDto.setUserId(entity.getUserId());
            setDto.setActivityName(entity.getActivityName());
            setDto.setUserName(entity.getUserName());

            responseListDto.add(setDto);
        }
        responseDto.setPariticipantListDto(responseListDto);
        responseDto.setActivityName(responseActName);

        return responseDto;
    }
}
