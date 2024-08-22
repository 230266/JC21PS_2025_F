package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.relational.core.sql.Join;
import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
import jp.co.jc21ps.activity_management.repository.JoinRequestRepository;
import jp.co.jc21ps.activity_management.repository.RegisterActivityRepository;
import jp.co.jc21ps.dto.JoinRequestDto;
import jp.co.jc21ps.dto.JoinRequestSaveDto;

@Service
public class JoinRequestService {

    @Autowired
    private final JoinRequestRepository joinRequestRepository;
    
    //リポジトリをセットする
     public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }

    //dto型のメソッドで返す
    public List<JoinRequestDto> findRequest(JoinRequestDto joinRequestDto) {

        //エンティティのインスタンス化
        JoinRequestEntity joinRequestEntity = new JoinRequestEntity();

        //エンティティにUserId,ClubIdを詰め替える
        joinRequestEntity.setUserId(joinRequestDto.getUserId());
        joinRequestEntity.setClubId(joinRequestDto.getClubId());

        //リポジトリのメソッドにエンティティに詰め替えたuserId,clubIdを渡す
        List<JoinRequestEntity> joinRequestList = joinRequestRepository.getJoinRequestById(joinRequestEntity);

        //結果のDtoリストを作成
        List<JoinRequestDto> joinRequestDtoList = new ArrayList<>();

        //リストが空でないことを確認して、エンティティからDtoへ変換
        for(JoinRequestEntity entity : joinRequestList){
            JoinRequestDto dto = new JoinRequestDto();
            //部署名、部署説明をdtoに渡す
            dto.setClubName(entity.getClubName());
            dto.setClubDescription(entity.getClubDescription());
            joinRequestDtoList.add(dto);
        }
        return joinRequestDtoList;
    }
    
    //インサートメソッド
    //public String insertJoinRequest(JoinRequestSaveDto joinRequestDto) throws Exception {
    // }
}
