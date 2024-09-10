package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    private final MessageSource messageSource;

    public JoinApprovalController(JoinApprovalService joinApprovalService, CommonService commonService,
            MessageSource messageSource) {
        this.joinApprovalService = joinApprovalService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView joinApproval(HttpSession session) {

        ModelAndView mav = new ModelAndView();

        SessionDto sessionDto = new SessionDto();
        sessionDto = commonService.getCommonService(session);
        String userId = sessionDto.getUserId();
        String leaderClubId = sessionDto.getClubId();

        try {
            // せっしょんのuserIdが空の時、ログイン画面に遷移する
            if (userId.isEmpty()) {
                mav.setViewName("redirect:/login");
                return mav;
            }

            // 取得できた場合、joinApprovalDtoにセットする
            JoinApprovalDto joinApprovalDto = new JoinApprovalDto();
            joinApprovalDto.setUserId(userId);
            joinApprovalDto.setClubId(leaderClubId);

            // serviceからgetJoinApprovalDataメソッド（画面表示用のメソッド）をセッションのuserId,clubIdを引数に取得する
            JoinApprovalNameDto viewList = joinApprovalService.getJoinApprovalData(joinApprovalDto);

            // Formに返す用のリスト
            List<JoinApprovalForm> viewData = new ArrayList<>();

            // さっき取得したviewList.getJoinApprovalDto()をdtoに詰める
            for (JoinApprovalDto dto : viewList.getJoinApprovalDto()) {
                JoinApprovalForm requestList = new JoinApprovalForm();
                requestList.setClubId(dto.getClubId());
                requestList.setUserId(dto.getUserId());
                requestList.setClubName(dto.getClubName());
                requestList.setUserName(dto.getUserName());

                viewData.add(requestList);
            }

            mav.addObject("clubName", viewList.getClubName());

            // 参加者がいなかった場合に活動名とメッセージだけ表示する
            String resultMessage = messageSource.getMessage("notrequest", null, Locale.getDefault());

            mav.addObject("message", resultMessage);
            mav.addObject("joinApprovalform", viewData);// List<JoinApprovalForm>をmavにいれて返す
            mav.addObject("leaderClubId", leaderClubId);

            // JoinApprovalという画面に遷移する
            mav.setViewName("JoinApproval");

        } catch (Exception e) {
            // セッションからclubIDを持ってきて、mavに詰めて返す
            sessionDto = commonService.getCommonService(session);
            // String leaderClubId = sessionDto.getClubId();
            mav.addObject("leaderClubId", leaderClubId);
            mav.setViewName("redirect:/error");
        }
        return mav;
    }

    // 否認
    @PostMapping("/Denial")
    public ModelAndView denial(JoinApprovalDataForm joinApprovalDataForm, HttpSession session) {
        JoinApprovalDataDto denialDto = new JoinApprovalDataDto();
        denialDto.setUserId(joinApprovalDataForm.getUserId());
        denialDto.setClubId(joinApprovalDataForm.getClubId());
        denialDto.setLeaderFlg(joinApprovalDataForm.isLeaderFlg());

        ModelAndView mav = new ModelAndView();

        SessionDto sessionDto = new SessionDto();
        sessionDto = commonService.getCommonService(session);
        String leaderClubId = sessionDto.getClubId();

        try {
            // 申請テーブルからdeleteするメソッドを呼び出す
            joinApprovalService.deleteRequest(denialDto);
            mav.addObject("leaderClubId", leaderClubId);
            mav.setViewName("redirect:/JoinApproval");
        } catch (Exception e) {
            mav.addObject("leaderClubId", leaderClubId);
            mav.setViewName("redirect:/error");
        }

        return mav;

    }

    // 承認
    @PostMapping("/Approval")
    public ModelAndView Approval(JoinApprovalDataForm joinApprovalDataForm, HttpSession session) {
        JoinApprovalDataDto denialDto = new JoinApprovalDataDto();
        denialDto.setUserId(joinApprovalDataForm.getUserId());
        denialDto.setClubId(joinApprovalDataForm.getClubId());
        denialDto.setLeaderFlg(joinApprovalDataForm.isLeaderFlg());

        ModelAndView mav = new ModelAndView();

        SessionDto sessionDto = new SessionDto();
        sessionDto = commonService.getCommonService(session);
        String leaderClubId = sessionDto.getClubId();

        try {
            // 部員テーブルにinsertするメソッドと申請テーブルから取り除くメソッドを呼び出す
            joinApprovalService.insertRequest(denialDto);
            joinApprovalService.deleteRequest(denialDto);
            mav.setViewName("redirect:/JoinApproval");
            mav.addObject("leaderClubId", leaderClubId);

        } catch (Exception e) {
            // メッセージ、ログ
            mav.setViewName("redirect:/error");
            mav.addObject("leaderClubId", leaderClubId);

        }

        return mav;

    }

}
