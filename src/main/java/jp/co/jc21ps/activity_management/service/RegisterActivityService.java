package jp.co.jc21ps.activity_management.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.entity.RegisterActivitySaveEntity;
import jp.co.jc21ps.activity_management.repository.RegisterActivityRepository;
import jp.co.jc21ps.activity_management.dto.RegisterActivityDto;
import jp.co.jc21ps.activity_management.dto.RegisterActivitySaveDto;

@Service
public class RegisterActivityService {

    private final RegisterActivityRepository registerActivityRepository;

    public RegisterActivityService(RegisterActivityRepository registerActivityRepository) {
        this.registerActivityRepository = registerActivityRepository;
    }

    // 初期表示
    public RegisterActivityDto findActivity(RegisterActivityDto paramDto) {

        // entityに値をセット
        RegisterActivityEntity activityEntity = new RegisterActivityEntity();
        activityEntity.setClubId(paramDto.getClubId());

        RegisterActivityEntity activity = registerActivityRepository.getActivityByClubId(activityEntity);

        // dtoに値をセット
        RegisterActivityDto responseDto = new RegisterActivityDto();
        responseDto.setClubName(activity.getClubName());

        return responseDto;
    }

    // 登録処理
    public String insertActivity(RegisterActivitySaveDto paramDto) throws Exception {

        RegisterActivitySaveEntity responseEntity = new RegisterActivitySaveEntity();

        // dtoから時間のデータを取得
        String date = paramDto.getActivityDate();
        String startTime = paramDto.getActivityStartTime();
        String endTime = paramDto.getActivityEndTime();

        // 日付と時間を組み合わせる
        String registStartTime = date + " " + startTime;
        String registEndTime = date + " " + endTime;

        // 日時をyyyy-MM-dd HH:mm形式に指定
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 日時文字列をLocalDateTimeに変換
        LocalDateTime startDateTime = LocalDateTime.parse(registStartTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(registEndTime, formatter);

        // maxParticipantをString型からint型に変換
        int maxParticipant = Integer.parseInt(paramDto.getMaxParticipant());

        // entityに値をセット
        responseEntity.setActivityId(paramDto.getActivityId());
        responseEntity.setActivityName(paramDto.getActivityName());
        responseEntity.setActivityPlace(paramDto.getActivityPlace());
        responseEntity.setActivityStartTime(startDateTime); // LocalDateTime型
        responseEntity.setActivityEndTime(endDateTime); // LocalDateTime型
        responseEntity.setActivityDescription(paramDto.getActivityDescription());
        responseEntity.setMaxParticipant(maxParticipant);
        responseEntity.setClubId(paramDto.getClubId());

        registerActivityRepository.saveActivity(responseEntity);

        // 活動登録成功のメッセージを返す
        return "activityRegisterCompleteMessage";

    }

    // シーケンスメソッド
    public String getNextActivityId() throws Exception {
        return registerActivityRepository.getNextActivityId();
    }
}
