package jp.co.jc21ps.activity_management.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;
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
    public RegisterActivityDto findActivity(RegisterActivityDto activityDto) {

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
    public void insertActivity(RegisterActivitySaveDto activityDto) {

        // 引数で指定するentityの作成(new)
        RegisterActivitySaveEntity activityEntity = new RegisterActivitySaveEntity();

        //dtoから時間のデータを取得し、変数に代入
        String date = activityDto.getActivityDate();
        String startTime = activityDto.getActivityStartTime();
        String endTime = activityDto.getActivityEndTime();
        
        //日付と時間を組み合わせる
        String registStartTime = date + " " + startTime;
        String registEndTime = date + " " + endTime; 

        //日時フォーマットの定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //文字列が指定されたフォーマットと一致しない場合や、無効な値が含まれている場合、エラーを投げる
        try {
            //日時文字列をLocalDateTimeに変換
            LocalDateTime startDateTime = LocalDateTime.parse(registStartTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(registEndTime, formatter);

            //maxParticipantをStringからintに変換する
            int maxParticipant = Integer.parseInt(activityDto.getMaxParticipant());
       
            //dto をentityに詰めなおす
            activityEntity.setActivityId(activityDto.getActivityId());
            activityEntity.setActivityName(activityDto.getActivityName());
            activityEntity.setActivityPlace(activityDto.getActivityPlace());
            activityEntity.setActivityStartTime(startDateTime); //LocalDateTime型
            activityEntity.setActivityEndTime(endDateTime);     //LocalDateTime型
            activityEntity.setActivityDescription(activityDto.getActivityDescription());
            activityEntity.setMaxParticipant(maxParticipant); //int型
            activityEntity.setClubId(activityDto.getClubId());

        }catch(DateTimeParseException e){
            e.printStackTrace(); //日時の形式が不正
        }

        //　リポジトリのインサートメソッドにエンティティを埋め込む
        registerActivityRepository.insertActivity(activityEntity);
    }
}