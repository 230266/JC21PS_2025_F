package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.jc21ps.activity_management.dto.JoinApprovalDataDto;
import jp.co.jc21ps.activity_management.dto.JoinApprovalDto;
import jp.co.jc21ps.activity_management.entity.JoinApprovalDataEntity;
import jp.co.jc21ps.activity_management.entity.JoinApprovalEntity;
import jp.co.jc21ps.activity_management.repository.JoinApprovalRepository;
import jp.co.jc21ps.dto.JoinApprovalNameDto;

@Service
public class JoinApprovalService {
    private final JoinApprovalRepository joinApprovalRepository;

    public JoinApprovalService(JoinApprovalRepository joinApprovalRepository) {
        this.joinApprovalRepository = joinApprovalRepository;
    }

    // 画面表示用
    public JoinApprovalNameDto getJoinApprovalData(JoinApprovalDto joinApprovalDto) {
        JoinApprovalEntity joinApprovalEntity = new JoinApprovalEntity();
        joinApprovalEntity.setClubId(joinApprovalDto.getClubId());
        joinApprovalEntity.setUserId(joinApprovalDto.getUserId());
        joinApprovalEntity.setUserName(joinApprovalDto.getUserName());
        joinApprovalEntity.setClubName(joinApprovalDto.getClubName());

        String clubName = joinApprovalRepository.getClubName(joinApprovalEntity);

        List<JoinApprovalEntity> approvalLists = joinApprovalRepository.getJoinApprovalList(joinApprovalEntity);

        // 取ってきた値をdtoを介してcontrollerに投げる用
        List<JoinApprovalDto> viewData = new ArrayList<>();
        for (JoinApprovalEntity entity : approvalLists) {
            JoinApprovalDto dto = new JoinApprovalDto();
            dto.setClubId(entity.getClubId());
            dto.setUserId(entity.getUserId());
            dto.setUserName(entity.getUserName());
            dto.setClubName(entity.getClubName());
            dto.setLeaderFlg(entity.isLeaderFlg());
            viewData.add(dto);
        }
        JoinApprovalNameDto joinApprovalNameDto = new JoinApprovalNameDto();
        joinApprovalNameDto.setJoinApprovalDto(viewData);
        joinApprovalNameDto.setClubName(clubName);

        return joinApprovalNameDto;
    }

    @Transactional
    public void deleteRequest(JoinApprovalDataDto joinApprovalDataDto) {
        JoinApprovalDataEntity joinApprovalDataEntity = new JoinApprovalDataEntity();
        joinApprovalDataEntity.setUserId(joinApprovalDataDto.getUserId());
        joinApprovalDataEntity.setClubId(joinApprovalDataDto.getClubId());
        joinApprovalDataEntity.setLeaderFlg(joinApprovalDataDto.isLeaderFlg());

        joinApprovalRepository.deleteRequest(joinApprovalDataEntity);
    }

    @Transactional
    public void insertRequest(JoinApprovalDataDto joinApprovalDataDto) {
        JoinApprovalDataEntity joinApprovalDataEntity = new JoinApprovalDataEntity();
        joinApprovalDataEntity.setUserId(joinApprovalDataDto.getUserId());
        joinApprovalDataEntity.setClubId(joinApprovalDataDto.getClubId());
        joinApprovalDataEntity.setLeaderFlg(joinApprovalDataDto.isLeaderFlg());

        joinApprovalRepository.insertRequest(joinApprovalDataEntity);
    }

}
