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

    // リポジトリをセットする
    public RegisterActivityService(RegisterActivityRepository registerActivityRepository, MessageSource messageSource) {
        this.registerActivityRepository = registerActivityRepository;
        this.messageSource = messageSource;
    }

    // dtoのインスタンス化
    // RegisterActivityDto activityDto = new RegisterActivityDto();

    // dto型のメソッドで返す
    public RegisterActivityDto findActivity(RegisterActivityDto paramDto) {

        // エンティティのインスタンス化
        RegisterActivityEntity activityEntity = new RegisterActivityEntity();

        // エンティティにClubIdを詰め替える
        activityEntity.setClubId(paramDto.getClubId());

        // リポジトリのメソッドにエンティティに詰め替えたclubIdを渡す
        RegisterActivityEntity activity = registerActivityRepository.getActivityByClubId(activityEntity);

        // レスポンスのDto
        RegisterActivityDto responseDto = new RegisterActivityDto();

        // 活動名だけdtoに渡す
        responseDto.setClubName(activity.getClubName());

        return responseDto;
    }

    // インサート
    public String insertActivity(RegisterActivitySaveDto paramDto) throws Exception {

        try {
            // 引数で指定するentityの作成(new)
            RegisterActivitySaveEntity responseEntity = new RegisterActivitySaveEntity();

            // dtoから時間のデータを取得し、変数に代入
            String date = paramDto.getActivityDate();
            String startTime = paramDto.getActivityStartTime();
            String endTime = paramDto.getActivityEndTime();

            // 日付と時間を組み合わせる
            String registStartTime = date + " " + startTime;
            String registEndTime = date + " " + endTime;

            // 日時フォーマットの定義
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // 文字列が指定されたフォーマットと一致しない場合や、無効な値が含まれている場合、エラーを投げる

            // 日時文字列をLocalDateTimeに変換
            LocalDateTime startDateTime = LocalDateTime.parse(registStartTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(registEndTime, formatter);

            // 存在する日時か検証
            if (startDateTime.isBefore(LocalDateTime.now()) || endDateTime.isBefore(LocalDateTime.now())) {
                // 過去の日付の場合はエラーメッセージをだす
                // return messageSource.getMessage("impossibleDate", null, Locale.getDefault());
                return "impossibleDate";
            } else {

                // maxParticipantをStringからintに変換する
                int maxParticipant = Integer.parseInt(paramDto.getMaxParticipant());

                // dto をentityに詰めなおす
                responseEntity.setActivityId(paramDto.getActivityId());
                responseEntity.setActivityName(paramDto.getActivityName());
                responseEntity.setActivityPlace(paramDto.getActivityPlace());
                responseEntity.setActivityStartTime(startDateTime); // LocalDateTime型
                responseEntity.setActivityEndTime(endDateTime); // LocalDateTime型
                responseEntity.setActivityDescription(paramDto.getActivityDescription());
                responseEntity.setMaxParticipant(maxParticipant);
                responseEntity.setClubId(paramDto.getClubId());

                // リポジトリのインサートメソッドにエンティティを埋め込む
                registerActivityRepository.saveActivity(responseEntity);

                // 成功のメッセージを返す
                return "activityRegisterCompleteMessage";
            }

        } catch (DateTimeParseException e) {
            return messageSource.getMessage("error.invalidDate", null, Locale.getDefault());
        }

    }

    // シーケンスメソッド
    public String getNextActivityId() throws Exception {
        return registerActivityRepository.getNextActivityId();
    }
}
