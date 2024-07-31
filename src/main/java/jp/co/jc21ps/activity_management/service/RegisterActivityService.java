package jp.co.jc21ps.activity_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.entity.Activity;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;
import jp.co.jc21ps.activity_management.form.RegisterActivityForm;
import jp.co.jc21ps.activity_management.repository.ActivityRepository;
import jp.co.jc21ps.activity_management.repository.RegisterActivityRepository;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;

@Service
public class RegisterActivityService {

     private final RegisterActivityRepository registerActivityRepository;

     //リポジトリをセットする
     public RegisterActivityService(RegisterActivityRepository registerActivityRepository) {
        this.registerActivityRepository = registerActivityRepository;
    }
    //dtoのインスタンス化
    RegisterActivityDto activityDto = new RegisterActivityDto();

    //dto型のメソッドで返す
    public RegisterActivityDto findActivityByClubId(RegisterActivityDto activityDto) {

        //エンティティのインスタンス化 
        RegisterActivityEntity activityEntity = new RegisterActivityEntity();

        //エンティティにClubIdを詰め替える
        activityEntity.setClubId(activityDto.getClubId());

        //リポジトリのメソッドにエンティティに詰め替えたclubIdを渡す
        RegisterActivityEntity activity = registerActivityRepository.getActivityByClubId(activityEntity);

        //活動名だけdtoに渡す
        activityDto.setClubName(activity.getClubName());

        return activityDto;
    }

    //インサートメソッド
    public void insert(RegisterActivitySaveDto activityDto){

        // 引数で指定するentityの作成(new)
        RegisterActivitySaveEntity activityEntity = new RegisterActivitySaveEntity();
        
        //　dto をentityに詰めなおす
        activityEntity.setActivityId(activityDto.getActivityId());
        activityEntity.setActivityName(activityDto.getActivityName());
        activityEntity.setActivityPlace(activityDto.getActivityPlace());
        activityEntity.setActivityStartTime(activityDto.getActivityStartTime());
        activityEntity.setActivityEndTime(activityDto.getActivityEndTime());
        activityEntity.setActivityDescription(activityDto.getActivityDescription());
        activityEntity.setMaxParticipant(activityDto.getMaxParticipant());
        activityEntity.setClubId(activityDto.getClubId());

        //　リポジトリのメソッド(インサートのやつ)を呼ぶ
        registerActivityRepository.insertActivity(activityEntity);
    }
}