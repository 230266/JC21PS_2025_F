package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.JoinRequestEntity;
import jp.co.jc21ps.activity_management.entity.JoinRequestSaveEntity;
import jp.co.jc21ps.activity_management.repository.JoinRequestRepository;
import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
import jp.co.jc21ps.activity_management.dto.JoinRequestSaveDto;

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
            dto.setClubId(entity.getClubId());
            joinRequestDtoList.add(dto);
        }
        return joinRequestDtoList;
    }
    
    //インサートメソッド
    public boolean insertJoinRequest(JoinRequestSaveDto joinRequestSaveDto) {
        try{
            //boolean = true の場合
            JoinRequestSaveEntity joinRequestSaveEntity = new JoinRequestSaveEntity();
            joinRequestSaveEntity.setUserId(joinRequestSaveDto.getUserId());
            joinRequestSaveEntity.setClubId(joinRequestSaveDto.getClubId());

            //リポジトリのインサートメソッドにエンティティを埋め込む
            joinRequestRepository.insertClub(joinRequestSaveEntity);
            
            //成功のメッセージを返す
            return true;
        
        }catch(Exception e){
            //boolean = false の場合
            //申請できなかったらエラー画面に遷移か、エラーメッセージ出す
            e.printStackTrace();
            return false;
        }
    }
}
