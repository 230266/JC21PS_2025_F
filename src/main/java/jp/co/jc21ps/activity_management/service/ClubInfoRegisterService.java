package jp.co.jc21ps.activity_management.service;

import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.dto.ClubInfoRegisterDto;
import jp.co.jc21ps.activity_management.entity.ClubInfoRegisterEntity;
import jp.co.jc21ps.activity_management.repository.ClubInfoRegisterRepository;

@Service
public class ClubInfoRegisterService {
    private final ClubInfoRegisterRepository clubInfoRegisterRepository;

    public ClubInfoRegisterService(ClubInfoRegisterRepository clubInfoRegisterRepository) {
        this.clubInfoRegisterRepository = clubInfoRegisterRepository;
    }

    // 初期画面表示
    public ClubInfoRegisterDto getClubInfoByClubId(ClubInfoRegisterDto paramDto) {

        // entityに値をセット
        ClubInfoRegisterEntity entity = new ClubInfoRegisterEntity();
        entity.setLeaderClubId(paramDto.getLeaderClubId());

        ClubInfoRegisterEntity clubInfoRegister = clubInfoRegisterRepository.getClubInfo(entity);

        // dtoに値をセット
        ClubInfoRegisterDto responseDto = new ClubInfoRegisterDto();
        responseDto.setClubName(clubInfoRegister.getClubName());
        responseDto.setClubDescription(clubInfoRegister.getClubDescription());

        return responseDto;
    }

    // 活動説明更新
    public String updateClubInfo(ClubInfoRegisterDto paramDto) throws Exception {

        // entityに値をセット
        ClubInfoRegisterEntity responseEntity = new ClubInfoRegisterEntity();
        responseEntity.setLeaderClubId(paramDto.getLeaderClubId());
        responseEntity.setClubDescription(paramDto.getClubDescription());

        clubInfoRegisterRepository.updateClubInfo(responseEntity);

        // 成功のメッセージを返す
        return "updateClubInfo";

    }
}
