package jp.co.jc21ps.activity_management.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    public TopService(TopRepository topRepository, MessageSource messageSource) {
        this.topRepository = topRepository;
        this.messageSource = messageSource;
    }

    // 活動の参加状態を呼び出す
    public boolean getActivityParticipationStatus(TopDataDto paramDto) {

        // entityに値をセット
        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());

        int participants = topRepository.isActivityParticipating(paramEntity);

        // 初期値をfalseで指定
        boolean flg = false;

        // 参加している場合、trueで返す
        if (participants > 0) {
            flg = true;
        }

        return flg;

    }

    // 不参加メソッド呼び出し
    @Transactional
    public void deleteActivity(TopDataDto paramDto) {

        // entityに値をセット
        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());
        topRepository.deleteActivity(paramEntity);

    }

    // 参加メソッド呼び出し
    @Transactional
    public void insertActivity(TopDataDto paramDto) {

        // entityに値をセット
        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());
        paramEntity.setClubId(paramDto.getClubId());

        // 活動の上限人数を取得
        Integer maxParticipants = topRepository.getMaxParticipants(paramEntity);

        // 現在の参加者数を取得
        int currentParticipants = topRepository.isActivityParticipating(paramEntity);

        // 活動の上限人数が現在の参加者数を上回っている場合、エラーメッセージを投げる
        if (currentParticipants > maxParticipants) {
            // messages.propertiesからメッセージを取得
            String errorMessage = messageSource.getMessage("participation.limit.exceeded", null,
                    LocaleContextHolder.getLocale());
            throw new ParticipationLimitExceededException(errorMessage);
        }

        topRepository.saveActivity(paramEntity);

    }

    // 初期画面表示
    public List<TopDto> getTopData(TopDto paramDto) {

        // entityに値をセット
        TopEntity paramEntity = new TopEntity();
        paramEntity.setUserId(paramDto.getUserId());

        List<TopEntity> topData = topRepository.getTopData(paramEntity);
        List<TopDto> responseDto = new ArrayList<>();

        for (TopEntity entity : topData) {

            // dtoに値をセット
            TopDto setDto = new TopDto();
            setDto.setNo(entity.getNo());
            setDto.setClubId(entity.getClubId());
            setDto.setClubName(entity.getClubName());
            setDto.setActivityId(entity.getActivityId());
            setDto.setActivityName(entity.getActivityName());
            setDto.setActivityPlace(entity.getActivityPlace());
            setDto.setDispActivityDate(entity.getDispActivityDate());
            setDto.setActivityStartTime(entity.getActivityStartTime());
            setDto.setActivityEndTime(entity.getActivityEndTime());
            setDto.setActivityDescription(entity.getActivityDescription());
            setDto.setParticipantsCount(entity.getParticipantsCount());
            setDto.setMaxParticipant(entity.getMaxParticipant());
            setDto.setIsParticipationFlg(entity.getIsParticipationFlg());

            // 過半数フラグの設定
            int participantsCount = entity.getParticipantsCount();
            int maxParticipant = 0;

            try {
                maxParticipant = Integer.parseInt(entity.getMaxParticipant());

            } catch (NumberFormatException e) {
                // maxParticipantがint型に変換できない場合、エラーメッセージを表示
                System.err.println("Invalid format for maxParticipant: " + e.getMessage());

                // 必要に応じてデフォルト値を設定するなどの処理を追加
                // デフォルト値として0を設定
                maxParticipant = 0;
            }

            // 参加者数が上限人数の過半数を超えているかどうかを判断する
            setDto.setIsMajorityFlg(maxParticipant > 0 && participantsCount >= (maxParticipant / 2.0));
            responseDto.add(setDto);
        }

        return responseDto;
    }

}