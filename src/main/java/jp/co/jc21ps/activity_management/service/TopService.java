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
    public boolean getActivityParticipationStatus(TopDataDto topDataDto) {
        // TopDataEntityを呼び出し、Dtoでゲットした活動ID、ユーザーIDをセットする
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());

        // repositoryから該当するデータが何件あるかを確認したメソッドを呼び出す
        int participants = topRepository.isActivityParticipating(topDataEntity);

        // 初期値をfalseで指定する
        boolean flg = false;

        // 0より大きい(参加している)場合trueにして返す
        if (participants > 0) {
            flg = true;
        }

        // そのままなら参加していないのでfalseを返す
        return flg;

    }

    // デリート呼び出し
    @Transactional
    public void deleteActivity(TopDataDto topDataDto) {
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());

        // RepositoryのdeleteActivityを呼び出す
        topRepository.deleteActivity(topDataEntity);
    }

    // インサート呼び出し
    @Transactional
    public void insertActivity(TopDataDto topDataDto) {
        TopDataEntity topDataEntity = new TopDataEntity();
        topDataEntity.setActivityId(topDataDto.getActivityId());
        topDataEntity.setUserId(topDataDto.getUserId());

        // アクティビティの上限人数を取得
        Integer maxParticipants = topRepository.getMaxParticipants(topDataEntity);

        // 現在の参加者数を取得
        int currentParticipants = topRepository.isActivityParticipating(topDataEntity);

        if (currentParticipants > maxParticipants) {
            String errorMessage = messageSource.getMessage("participation.limit.exceeded", null,
                    LocaleContextHolder.getLocale());
            throw new ParticipationLimitExceededException(errorMessage);
        }

        // RepositoryのinsertActivityを呼び出す
        topRepository.insertActivity(topDataEntity);
    }

    // 画面表示用
    public List<TopDto> getTopData(TopDto topDto) {
        TopEntity topEntity = new TopEntity();
        topEntity.setUserId(topDto.getUserId());

        List<TopEntity> tops = topRepository.getTop(topEntity);

        // 取ってきた値をdtoを介してcontrollerに投げる用
        List<TopDto> viewData = new ArrayList<>();

        // TopEntityからTopDtoに変換し、リストに追加
        for (TopEntity entity : tops) {

            // TopDto型のDtoに値を詰めている
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

            dto.setIsParticipationFlg(entity.getIsParticipationFlg());

            // 過半数フラグの設定
            int participantsCount = entity.getParticipantsCount();
            int maxParticipant = 0;

            try {
                maxParticipant = Integer.parseInt(entity.getMaxParticipant());
            } catch (NumberFormatException e) {

                // エラー処理: maxParticipant が数値に変換できない場合
                System.err.println("Invalid format for maxParticipant: " + e.getMessage());

                // 必要に応じてデフォルト値を設定するなどの処理を追加
                maxParticipant = 0; // デフォルト値として0を設定
            }

            dto.setIsMajorityFlg(maxParticipant > 0 && participantsCount >= (maxParticipant / 2.0));

            viewData.add(dto);
        }

        return viewData;
    }
}