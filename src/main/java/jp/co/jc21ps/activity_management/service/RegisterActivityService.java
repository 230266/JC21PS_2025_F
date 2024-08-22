package jp.co.jc21ps.activity_management.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;
import jp.co.jc21ps.activity_management.repository.RegisterActivityRepository;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;
import java.util.Locale;

@Service
public class RegisterActivityService {

     @Autowired
     private final RegisterActivityRepository registerActivityRepository;
     @Autowired
     private final MessageSource messageSource;

     //リポジトリをセットする
     public RegisterActivityService(RegisterActivityRepository registerActivityRepository, MessageSource messageSource) {
        this.registerActivityRepository = registerActivityRepository;
        this.messageSource = messageSource;
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
    public String insertActivity(RegisterActivitySaveDto activityDto) throws Exception {

        try {
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            //文字列が指定されたフォーマットと一致しない場合や、無効な値が含まれている場合、エラーを投げる
        
            //日時文字列をLocalDateTimeに変換
            LocalDateTime startDateTime = LocalDateTime.parse(registStartTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(registEndTime, formatter);

            //存在する日時か検証
            if(startDateTime.isBefore(LocalDateTime.now()) || endDateTime.isBefore(LocalDateTime.now())) {
                //過去の日付の場合はエラーメッセージをだす
                //return messageSource.getMessage("impossibleDate", null, Locale.getDefault());
                return "impossibleDate";
            } else{

                //maxParticipantをStringからintに変換する
                int maxParticipant = Integer.parseInt(activityDto.getMaxParticipant());
        
                //dto をentityに詰めなおす
                activityEntity.setActivityId(activityDto.getActivityId());
                activityEntity.setActivityName(activityDto.getActivityName());
                activityEntity.setActivityPlace(activityDto.getActivityPlace());
                activityEntity.setActivityStartTime(startDateTime); //LocalDateTime型
                activityEntity.setActivityEndTime(endDateTime);     //LocalDateTime型
                activityEntity.setActivityDescription(activityDto.getActivityDescription());
                //activityEntity.setMaxParticipant(maxParticipant); //int型
                activityEntity.setMaxParticipant(maxParticipant);
                activityEntity.setClubId(activityDto.getClubId());

            //activitySaveDto.setMaxParticipant("6");

                //　リポジトリのインサートメソッドにエンティティを埋め込む
                registerActivityRepository.insertActivity(activityEntity);

                //成功のメッセージを返す
                //return messageSource.getMessage("activityRegisterCompleteMessage", null, Locale.getDefault());
                return "activityRegisterCompleteMessage";
            }

        }catch(DateTimeParseException e){
            return messageSource.getMessage("error.invalidDate", null, Locale.getDefault());
        }

    }

    //シーケンスメソッド
    public String getNextActivityId() throws Exception {
        return registerActivityRepository.getNextActivityId();
    }
}
