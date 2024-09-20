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

    // TopRepositoryを引数にする
    public TopService(TopRepository topRepository, MessageSource messageSource) {
        this.topRepository = topRepository;
        this.messageSource = messageSource;
    }

    // アクティビティの参加状態を取得
    public boolean getActivityParticipationStatus(TopDataDto paramDto) {
        // TopDataEntityを呼び出し、Dtoでゲットした活動ID、ユーザーIDをセットする
        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());

        // repositoryから該当するデータが何件あるかを確認したメソッドを呼び出す
        int participants = topRepository.isActivityParticipating(paramEntity);

        // 初期値をfalseで指定する
        boolean flg = false;

        // 0より大きい(参加している)場合trueにして返す
        if (participants != 0) {
            flg = true;
        }

        // そのままなら参加していないのでfalseを返す
        return flg;

    }

    // デリート呼び出し
    @Transactional
    public void deleteActivity(TopDataDto paramDto) {
        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());

        // RepositoryのdeleteActivityを呼び出す
        topRepository.deleteActivity(paramEntity);
    }

    // インサート呼び出し
    @Transactional
    public void insertActivity(TopDataDto paramDto) {

        TopDataEntity paramEntity = new TopDataEntity();
        paramEntity.setActivityId(paramDto.getActivityId());
        paramEntity.setUserId(paramDto.getUserId());
        paramEntity.setClubId(paramDto.getClubId());

        // アクティビティの上限人数を取得
        Integer maxParticipants = topRepository.getMaxParticipants(paramEntity);

        // 現在の参加者数を取得
        int currentParticipants = topRepository.isActivityParticipating(paramEntity);

        if (currentParticipants > maxParticipants) {
            String errorMessage = messageSource.getMessage("participation.limit.exceeded", null,
                    LocaleContextHolder.getLocale());
            throw new ParticipationLimitExceededException(errorMessage);
        }

        // RepositoryのinsertActivityを呼び出す
        topRepository.saveActivity(paramEntity);
    }

    // 画面表示用
    public List<TopDto> getTopData(TopDto paramDto) {
        TopEntity paramEntity = new TopEntity();
        paramEntity.setUserId(paramDto.getUserId());

        List<TopEntity> topData = topRepository.getTopData(paramEntity);

        // 取ってきた値をdtoを介してcontrollerに投げる用
        List<TopDto> responseDto = new ArrayList<>();

        // TopEntityからTopDtoに変換し、リストに追加
        for (TopEntity entity : topData) {

            // TopDto型のDtoに値を詰めている
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
                // エラー処理: maxParticipant が数値に変換できない場合
                System.err.println("Invalid format for maxParticipant: " + e.getMessage());

                // 必要に応じてデフォルト値を設定するなどの処理を追加
                // デフォルト値として0を設定
                maxParticipant = 0;
            }

            setDto.setIsMajorityFlg(maxParticipant > 0 && participantsCount >= (maxParticipant / 2.0));
            responseDto.add(setDto);
        }
        return responseDto;
    }
}