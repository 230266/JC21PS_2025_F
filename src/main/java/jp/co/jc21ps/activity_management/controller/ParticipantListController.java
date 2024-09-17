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
@RequestMapping("/participantList")
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
    public ModelAndView dispParticipantList(@RequestParam(value = "activityId", required = true) String activityId,
            HttpSession session) {

        // ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        // リクエストパラメータから送られてくる活動IDが存在しない場合、エラー画面に遷移
        if (activityId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // セッションからユーザーID、部署IDを取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String userId = sessionDto.getUserId();
        String leaderClubId = sessionDto.getClubId();

        // ユーザーIDがセッションに存在しない場合、エラー画面に遷移
        if (userId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // dtoのインスタンス化
        ParticipantListDto setSessionDto = new ParticipantListDto();

        // dtoに活動ID、ユーザーIDを詰め替える
        setSessionDto.setActivityId(activityId);
        setSessionDto.setUserId(userId);

        try {
            // サービスのメソッドでデータを取得
            ParticipantDto viewData = participantListService.getParticipantListData(setSessionDto);
            List<ParticipantListForm> responseListForm = new ArrayList<>();

            for (ParticipantListDto dto : viewData.getPariticipantListDto()) {
                ParticipantListForm responseForm = new ParticipantListForm();
                responseForm.setActivityId(dto.getActivityId());
                responseForm.setUserId(dto.getUserId());
                responseForm.setUserName(dto.getUserName());
                responseForm.setActivityName(dto.getActivityName());

                responseListForm.add(responseForm);
            }

            // 参加者がいる場合の表示
            mav.addObject("participantListForm", responseListForm);

            // 参加者がいない場合、活動名だけ表示する
            mav.addObject("activityName", viewData.getActivityName());

            // メッセージ
            String resultMessage = messageSource.getMessage("notpariticipant", null, Locale.getDefault());
            mav.addObject("message", resultMessage);

            // 部長クラブID
            mav.addObject("leaderClubId", leaderClubId);

            // html(View)の名前を指定する
            mav.setViewName("participantList");

        } catch (Exception e) {
            mav.setViewName("error");
        }
        return mav;

    }
}