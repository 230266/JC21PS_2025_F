package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;

import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.JoinApprovalDataDto;
import jp.co.jc21ps.activity_management.dto.JoinApprovalDto;
import jp.co.jc21ps.activity_management.form.JoinApprovalDataForm;
import jp.co.jc21ps.activity_management.form.JoinApprovalForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.JoinApprovalService;
import jp.co.jc21ps.dto.JoinApprovalNameDto;
import jp.co.jc21ps.dto.SessionDto;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/JoinApproval")
public class JoinApprovalController {
    private final JoinApprovalService joinApprovalService;
    private final CommonService commonService;
    @Autowired
    private final MessageSource messageSource;

    public JoinApprovalController(JoinApprovalService joinApprovalService, CommonService commonService,
            MessageSource messageSource) {
        this.joinApprovalService = joinApprovalService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView joinApproval(HttpSession session, Model model) {
        ModelAndView mav = new ModelAndView();
        try {

            SessionDto sessionDto = new SessionDto();
            sessionDto = commonService.getCommonService(session);
            String userId = sessionDto.getUserId();
            String clubId = sessionDto.getClubId();

            if (userId.isEmpty()) {
                mav.setViewName("redirect:/error");
                return mav;
            }

            JoinApprovalDto joinApprovalDto = new JoinApprovalDto();
            joinApprovalDto.setUserId(userId);
            joinApprovalDto.setClubId(clubId);

            JoinApprovalNameDto viewList = joinApprovalService.getJoinApprovalData(joinApprovalDto);

            List<JoinApprovalForm> viewData = new ArrayList<>();

            for (JoinApprovalDto dto : viewList.getJoinApprovalDto()) {
                JoinApprovalForm requestList = new JoinApprovalForm();
                requestList.setClubId(dto.getClubId());
                requestList.setUserId(dto.getUserId());
                requestList.setClubName(dto.getClubName());
                requestList.setUserName(dto.getUserName());

                viewData.add(requestList);
            }

            mav.addObject("clubName", viewList.getClubName());

            // 参加者がいなかった場合に活動名だけ表示する
            String resultMessage = messageSource.getMessage("notrequest", null, Locale.getDefault());
            mav.addObject("message", resultMessage);
            mav.addObject("joinApprovalform", viewData);
            mav.setViewName("JoinApproval");

        } catch (Exception e) {
            mav.setViewName("redirect:/error");
        }
        return mav;
    }

    @PostMapping("/Denial")
    public ModelAndView denial(JoinApprovalDataForm joinApprovalDataForm) {
        JoinApprovalDataDto denialDto = new JoinApprovalDataDto();
        denialDto.setUserId(joinApprovalDataForm.getUserId());
        denialDto.setClubId(joinApprovalDataForm.getClubId());
        denialDto.setLeaderFlg(joinApprovalDataForm.isLeaderFlg());

        joinApprovalService.deleteRequest(denialDto);
        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/JoinApproval");

        return mav;

    }

    @PostMapping("/Approval")
    public ModelAndView Approval(JoinApprovalDataForm joinApprovalDataForm) {
        JoinApprovalDataDto denialDto = new JoinApprovalDataDto();
        denialDto.setUserId(joinApprovalDataForm.getUserId());
        denialDto.setClubId(joinApprovalDataForm.getClubId());
        denialDto.setLeaderFlg(joinApprovalDataForm.isLeaderFlg());
        ModelAndView mav = new ModelAndView();

        try {
            joinApprovalService.insertRequest(denialDto);
            joinApprovalService.deleteRequest(denialDto);
            mav.setViewName("redirect:/JoinApproval");
        } catch (Exception e) {
            mav.setViewName("redirect:/error");

        }

        return mav;

    }

}
