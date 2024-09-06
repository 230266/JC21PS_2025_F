package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import jp.co.jc21ps.activity_management.dto.ParticipantListDto;
import jp.co.jc21ps.activity_management.form.ParticipantListForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.ParticipantListService;
import jp.co.jc21ps.dto.ParticipantDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ParticipantList")
public class ParticipantListController {
    private final ParticipantListService participantListService;
    private final CommonService commonService;
    @Autowired
    private final MessageSource messageSource;

    public ParticipantListController(ParticipantListService participantListService, CommonService commonService,
            MessageSource messageSource) {
        this.participantListService = participantListService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView ParticipantList(@RequestParam(value = "activityId", required = true) String activityId,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            if (activityId.isEmpty()) {
                mav.setViewName("redirect:/error");
                return mav;
            }

            SessionDto sessionDto = new SessionDto();
            //CommonServiceという別クラスを作り、そこからDto経由でセッションを持ってきている
            sessionDto = commonService.getCommSessionDto(session);
            String userId =  sessionDto.getUserId();

            if (userId.isEmpty()) {
                mav.setViewName("redirect:/error");
                return mav;
            }

            ParticipantListDto participantListDto = new ParticipantListDto();
            participantListDto.setActivityId(activityId);
            participantListDto.setUserId(userId);

            ParticipantDto viewList = participantListService.getListData(participantListDto);
            List<ParticipantListForm> participantList = new ArrayList<>();

            for (ParticipantListDto dto : viewList.getPariticipantDto()) {
                ParticipantListForm viewsetlist = new ParticipantListForm();
                viewsetlist.setActivityId(dto.getActivityId());
                viewsetlist.setUserId(dto.getUserId());
                viewsetlist.setUserName(dto.getUserName());
                viewsetlist.setActivityName(dto.getActivityName());

                participantList.add(viewsetlist);
            }

            mav.addObject("activityName", viewList.getActivityName());

            // 参加者がいなかった場合に活動名だけ表示する
            String resultMessage = messageSource.getMessage("notpariticipant", null, Locale.getDefault());

            // メッセージ移動
            mav.addObject("message", resultMessage);

            // 参加者がいる場合の表示
            mav.addObject("participantListForm", participantList);

            mav.setViewName("ParticipantList");
        } catch (Exception e) {

            mav.setViewName("redirect:/error");
        }
        return mav;

    }
}