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

    // dto型のメソッドで返す
    public ClubInfoRegisterDto getClubInfoByClubId(ClubInfoRegisterDto paramDto) {

        // entityをnew
        ClubInfoRegisterEntity entity = new ClubInfoRegisterEntity();

        // entityにleaderclubIdを詰め替える
        entity.setLeaderClubId(paramDto.getLeaderClubId());

        // リポジトリのメソッドにエンティティに詰め替えたleaderclubIdを渡す
        ClubInfoRegisterEntity clubInfoRegister = clubInfoRegisterRepository.getClubInfo(entity);

        // 部署名、部署説明をdtoに渡す
        ClubInfoRegisterDto responseDto = new ClubInfoRegisterDto();
        responseDto.setClubName(clubInfoRegister.getClubName());
        responseDto.setClubDescription(clubInfoRegister.getClubDescription());
        return responseDto;
    }

    // アップデート
    public String updateClubInfo(ClubInfoRegisterDto paramDto) throws Exception {

        ClubInfoRegisterEntity responseEntity = new ClubInfoRegisterEntity();

        responseEntity.setLeaderClubId(paramDto.getLeaderClubId());
        responseEntity.setClubDescription(paramDto.getClubDescription());

        clubInfoRegisterRepository.updateClubInfo(responseEntity);

        // 成功のメッセージを返す
        return "updateClubInfo";

    }
}
