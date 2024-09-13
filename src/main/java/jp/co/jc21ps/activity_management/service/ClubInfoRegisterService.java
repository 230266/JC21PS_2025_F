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
    public ClubInfoRegisterDto getClubInfoByClubId(ClubInfoRegisterDto dto) {

        // entityをnew
        ClubInfoRegisterEntity entity = new ClubInfoRegisterEntity();

        // entityにleaderclubIdを詰め替える
        entity.setLeaderClubId(dto.getLeaderClubId());

        // リポジトリのメソッドにエンティティに詰め替えたleaderclubIdを渡す
        ClubInfoRegisterEntity clubInfoRegister = clubInfoRegisterRepository.getClubInfoByClubId(entity);

        // 部署名、部署説明をdtoに渡す
        dto.setClubName(clubInfoRegister.getClubName());
        dto.setClubDescription(clubInfoRegister.getClubDescription());
        return dto;
    }

    // アップデート
    public String updateClubInfo(ClubInfoRegisterDto clubInfoRegisterDto) throws Exception {

        ClubInfoRegisterEntity clubInfoRegisterEntity = new ClubInfoRegisterEntity();

        clubInfoRegisterEntity.setLeaderClubId(clubInfoRegisterDto.getLeaderClubId());
        clubInfoRegisterEntity.setClubDescription(clubInfoRegisterDto.getClubDescription());

        clubInfoRegisterRepository.updateClubInfo(clubInfoRegisterEntity);

        // 成功のメッセージを返す
        return "updateClubInfo";

    }
}
