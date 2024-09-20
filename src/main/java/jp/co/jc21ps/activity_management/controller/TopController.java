package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.TopDataDto;
import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.form.TopForm;
import jp.co.jc21ps.activity_management.form.RegisterActivitySaveForm;
import jp.co.jc21ps.activity_management.form.TopDataForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.ParticipationLimitExceededException;
import jp.co.jc21ps.activity_management.service.TopService;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/top")
public class TopController {

    private final TopService topService;
    private final CommonService commonService;
    private final MessageSource messageSource;

    public TopController(TopService topService, CommonService commonService, MessageSource messageSource) {
        this.topService = topService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView dispTop(HttpSession session, RegisterActivitySaveForm paramForm,
            @ModelAttribute("activityRegisterCompleteMessage") String activityRegisterCompleteMessage) {

        ModelAndView mav = new ModelAndView();

        try {
            // セッションからuserId, clubIdを取得
            SessionDto sessionDto = commonService.getSessionDto(session);
            String userId = sessionDto.getUserId();
            String leaderClubId = sessionDto.getClubId();

            // セッションが切れた場合、エラー画面に遷移
            if (userId == null) {
                mav.setViewName("error");
                return mav;
            }

            // dtoに値をセット
            TopDto topDto = new TopDto();
            topDto.setUserId(userId);

            List<TopDto> topDataList = topService.getTopData(topDto);
            List<TopForm> responseForm = new ArrayList<>();

            // formに値をセット
            for (TopDto form : topDataList) {
                TopForm setTopData = new TopForm();
                setTopData.setNo(form.getNo());
                setTopData.setClubId(form.getClubId());
                setTopData.setClubName(form.getClubName());
                setTopData.setActivityId(form.getActivityId());
                setTopData.setActivityName(form.getActivityName());
                setTopData.setActivityPlace(form.getActivityPlace());
                setTopData.setDispActivityDate(form.getDispActivityDate());
                setTopData.setDispActivityTime(form.getDispActivityTime());
                setTopData.setActivityStartTime(form.getActivityStartTime());
                setTopData.setActivityEndTime(form.getActivityEndTime());
                setTopData.setActivityDescription(form.getActivityDescription());
                setTopData.setParticipantsCount(form.getParticipantsCount());
                setTopData.setMaxParticipant(form.getMaxParticipant());
                setTopData.setIsParticipationFlg(form.getIsParticipationFlg());
                setTopData.setIsMajorityFlg(form.getIsMajorityFlg());

                // responseFormにリストを追加
                responseForm.add(setTopData);
            }
            paramForm.setMessage(activityRegisterCompleteMessage);
            if (!ObjectUtils.isEmpty(paramForm)) {
                mav.addObject("activityRegisterCompleteMessage", paramForm.getMessage());
            }

            // messages.propertiesからメッセージを取得
            String resultMessage = messageSource.getMessage("notactivitylist", null, Locale.getDefault());

            // 活動予定がない場合のメッセージ
            mav.addObject("message", resultMessage);
            mav.addObject("topform", responseForm);
            mav.addObject("leaderClubId", leaderClubId);
            // トップ画面に遷移
            mav.setViewName("top");
        } catch (Exception e) {
            // DB接続に失敗した場合、エラー画面に遷移
            mav.setViewName("error");
        }
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView toggleParticipation(TopDataForm paramForm) {

        ModelAndView mav = new ModelAndView();

        // dtoに値をセット
        TopDataDto paramDto = new TopDataDto();
        paramDto.setActivityId(paramForm.getActivityId());
        paramDto.setUserId(paramForm.getUserId());
        paramDto.setClubId(paramForm.getClubId());

        try {
            // フラグで参加可否を判定
            boolean isParticipating = topService.getActivityParticipationStatus(paramDto);

            if (isParticipating) {
                // 参加している場合は、削除
                topService.deleteActivity(paramDto);
            } else {
                try {
                    // 参加していない場合は、追加
                    topService.insertActivity(paramDto);

                    // 活動の参加者人数が上限に達した場合、エラーメッセージを表示する
                } catch (ParticipationLimitExceededException e) {
                    mav.addObject("errorMessage", e.getMessage());
                    mav.setViewName("top");
                    return mav;
                }
            }
            // 処理に成功した場合、トップ画面へリダイレクト
            mav.setViewName("redirect:/top");

        } catch (Exception e) {
            // DB接続に失敗した場合、エラー画面へ遷移
            mav.setViewName("error");
        }
        return mav;
    }
}
