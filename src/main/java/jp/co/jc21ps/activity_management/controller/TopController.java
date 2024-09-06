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
import jp.co.jc21ps.dto.SessionDto;

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

    @PostMapping("/save")
    public ModelAndView toggleParticipation(TopDataForm topDataForm) {
        ModelAndView mav = new ModelAndView();
        TopDataDto topDataDto = new TopDataDto();
        topDataDto.setActivityId(topDataForm.getActivityId());
        topDataDto.setUserId(topDataForm.getUserId());

        // 参加している場合は削除、参加していない場合は追加
        try {
            boolean isParticipating = topService.getActivityParticipationStatus(topDataDto);

            if (isParticipating) {
                topService.deleteActivity(topDataDto);
            } else {
                try {
                    topService.insertActivity(topDataDto);
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
            mav.setViewName("redirect:/error");
        }

        return mav;
    }

    @GetMapping
    public ModelAndView top(HttpSession session, Model model) {
        ModelAndView mav = new ModelAndView();

        try {
            SessionDto sessionDto = new SessionDto();

            sessionDto = commonService.getCommonService(session);
            String userId = sessionDto.getUserId();

            if (userId.isEmpty()) {
                mav.setViewName("redirect:/error");
                return mav;
            }

            TopDto topDto = new TopDto();
            topDto.setUserId(userId);
            List<TopDto> viewAct = topService.getTopData(topDto);

            List<TopForm> actList = new ArrayList<>();

            for (TopDto lastForm : viewAct) {

                TopForm act = new TopForm();
                act.setNo(lastForm.getNo());
                act.setClubId(lastForm.getClubId());
                act.setClubName(lastForm.getClubName());
                act.setActivityId(lastForm.getActivityId());
                act.setActivityName(lastForm.getActivityName());
                act.setActivityPlace(lastForm.getActivityPlace());
                act.setDispActivityDate(lastForm.getDispActivityDate());
                act.setDispActivityTime(lastForm.getDispActivityTime());
                act.setActivityStartTime(lastForm.getActivityStartTime());
                act.setActivityEndTime(lastForm.getActivityEndTime());
                act.setActivityDescription(lastForm.getActivityDescription());
                act.setParticipantsCount(lastForm.getParticipantsCount());
                act.setMaxParticipant(lastForm.getMaxParticipant());
                act.setIsParticipationFlg(lastForm.getIsParticipationFlg());
                act.setIsMajorityFlg(lastForm.getIsMajorityFlg());

                actList.add(act);

            }
            String resultMessage = messageSource.getMessage("notactivitylist", null, Locale.getDefault());
            mav.addObject("message", resultMessage);
            mav.addObject("topform", actList);

            mav.setViewName("top");

        } catch (Exception e) {
            // 問題が発生した場合はエラーページにリダイレクト
            mav.setViewName("redirect:/error");
        }

        return mav;
    }
}
