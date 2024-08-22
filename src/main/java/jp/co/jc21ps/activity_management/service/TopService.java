package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.dto.TopDataDto;
import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.entity.TopDataEntity;
import jp.co.jc21ps.activity_management.repository.TopRepository;


@Service
public class TopService {
    
    private final TopRepository topRepository;

     
    public TopService(TopRepository topRepository){
        this.topRepository = topRepository;
    }

    // アクティビティの参加状態を取得
    public boolean getActivityParticipationStatus(TopDataDto topDataDto) {
        //TopDataEntityを呼び出し、Dtoでゲットした活動ID、ユーザーIDをセットする
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());
        
        //repositoryから該当するデータが何件あるかを確認したメソッドを呼び出す
        int aaa = topRepository.isActivityParticipating(topDataEntity);
        
        //初期値をfalseで指定する
        boolean aaaflg = false;
        
        //0より大きい(参加している)場合trueにして返す
        if(aaa > 0){
            aaaflg = true;
        }
        //そのままなら参加していないのでfalseを返す
        return aaaflg;
    }

    //デリート呼び出し
    @Transactional
    public void deleteActivity(TopDataDto topDataDto){
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());

        //RepositoryのdeleteActivityを呼び出す
        topRepository.deleteActivity(topDataEntity);
    }

    //インサート呼び出し
    @Transactional
    public void insertActivity(TopDataDto topDataDto){
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());
        
        //RepositoryのinsertActivityを呼び出す
        topRepository.insertActivity(topDataEntity);
     }

    //画面表示用
    public List<TopDto> getTopData(TopDto topDto){
        TopEntity topEntity = new TopEntity();
        topEntity.setUserId(topDto.getUserId());

        List<TopEntity> tops = topRepository.getTop(topEntity);
        
        //取ってきた値をdtoを介してcontrollerに投げる用
        List<TopDto> viewData = new ArrayList<>();
        
        //TopEntityからTopDtoに変換し、リストに追加
        for(TopEntity entity : tops){
            
            //TopDto型のDtoに値を詰めている
            TopDto dto = new TopDto();
            dto.setNo(entity.getNo());
            dto.setClubId(entity.getClubId());
            dto.setClubName(entity.getClubName());
            dto.setActivityId(entity.getActivityId());
            dto.setActivityName(entity.getActivityName());
            dto.setActivityPlace(entity.getActivityPlace());
            dto.setDispActivityDate(entity.getDispActivityDate());
            dto.setActivityStartTime(entity.getActivityStartTime());
            dto.setActivityEndTime(entity.getActivityEndTime());
            dto.setActivityDescription(entity.getActivityDescription());
            dto.setParticipantsCount(entity.getParticipantsCount());
            dto.setMaxParticipant(entity.getMaxParticipant());
            
            //dtoに受け渡すため、boolean型をStringに変える➡「～.toString()」
            dto.setIsParticipationFlg(entity.getIsParticipationFlg());
            //Dto.setIsMajorityFlg(Boolean.toString(entity.getIsMajorityFlg()));
        
            //TopDto型のリストviewDataに値を詰める
            viewData.add(dto);
        }
        
        //リストを返す(controllerで呼ぶ)
        return viewData;
    }
} 