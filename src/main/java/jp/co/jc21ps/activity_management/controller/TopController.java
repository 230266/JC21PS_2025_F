package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.TopDataDto;
import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.form.TopForm;
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

    @Autowired
    private final MessageSource messageSource;

    public TopController(TopService topService, CommonService commonService, MessageSource messageSource) {
        this.topService = topService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView dispTop(HttpSession session, Model model) {
        ModelAndView mav = new ModelAndView();
        try {
            // セッションから値を取得する
            SessionDto sessionDto = commonService.getSessionDto(session);
            String userId = sessionDto.getUserId();
            String leaderClubId = sessionDto.getClubId();

            if (userId == null) {
                // userIdがnullまたは空の場合はエラーページに遷移
                mav.setViewName("error");
                return mav;
            }

            // userIdをもとにデータを取得する
            TopDto topDto = new TopDto();
            topDto.setUserId(userId);
            List<TopDto> topDataList = topService.getTopData(topDto);

            List<TopForm> activityList = new ArrayList<>();

            for (TopDto dto : topDataList) {
                TopForm setTopData = new TopForm();

                setTopData.setNo(dto.getNo());
                setTopData.setClubId(dto.getClubId());
                setTopData.setClubName(dto.getClubName());
                setTopData.setActivityId(dto.getActivityId());
                setTopData.setActivityName(dto.getActivityName());
                setTopData.setActivityPlace(dto.getActivityPlace());
                setTopData.setDispActivityDate(dto.getDispActivityDate());
                setTopData.setDispActivityTime(dto.getDispActivityTime());
                setTopData.setActivityStartTime(dto.getActivityStartTime());
                setTopData.setActivityEndTime(dto.getActivityEndTime());
                setTopData.setActivityDescription(dto.getActivityDescription());
                setTopData.setParticipantsCount(dto.getParticipantsCount());
                setTopData.setMaxParticipant(dto.getMaxParticipant());
                setTopData.setIsParticipationFlg(dto.getIsParticipationFlg());
                setTopData.setIsMajorityFlg(dto.getIsMajorityFlg());
                activityList.add(setTopData);

            }

            String resultMessage = messageSource.getMessage("notactivitylist", null, Locale.getDefault());
            mav.addObject("message", resultMessage);
            mav.addObject("responseForm", activityList);
            mav.addObject("leaderClubId", leaderClubId);
            mav.setViewName("top");
        } catch (Exception e) {
            mav.setViewName("error");
        }
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView toggleParticipation(TopDataForm paramForm) {

        ModelAndView mav = new ModelAndView();
        TopDataDto paramDto = new TopDataDto();

        paramDto.setActivityId(paramForm.getActivityId());
        paramDto.setUserId(paramForm.getUserId());
        paramDto.setClubId(paramForm.getClubId());

        // 参加している場合は削除、参加していない場合は追加
        try {
            boolean isParticipating = topService.getActivityParticipationStatus(paramDto);
            if (isParticipating) {
                topService.deleteActivity(paramDto);
            } else {
                try {
                    topService.insertActivity(paramDto);
                } catch (ParticipationLimitExceededException e) {
                    mav.addObject("errorMessage", e.getMessage());
                    mav.setViewName("error");
                    return mav;
                }
            }
            // 取得できた場合はトップ画面に遷移
            mav.setViewName("redirect:/top");
        } catch (Exception e) {
            // 取得できなかった場合はエラー画面に遷移
            mav.setViewName("error");
        }
        return mav;
    }
}
