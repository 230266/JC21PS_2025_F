package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.MessageSource;
import jp.co.jc21ps.activity_management.dto.ParticipantListDto;
import jp.co.jc21ps.activity_management.form.ParticipantListForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.ParticipantListService;
import jp.co.jc21ps.activity_management.dto.ParticipantDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/participantList")
public class ParticipantListController {

    private final ParticipantListService participantListService;
    private final CommonService commonService;
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

        ModelAndView mav = new ModelAndView();

        // 活動IDが存在しない場合、エラー画面に遷移
        if (activityId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        /*
         * TODO ➊ セッションからuserId, clubIdを取得
         * 1.commonServiceからgetSessionDtoメソッドを呼び出す。(SessionDto型の変数を宣言すること。)
         * 2.userId(String)に、getSessionDtoから取得したuserIdを格納する。(ヒント:getUserId()を使用する。)
         * 3.leaderClubId(String)に、getSessionDtoから取得したleaderClubIdを格納する。
         */
        SessionDto sessionDto = commonService.getSessionDto(session);
        String userId = sessionDto.getUserId();
        String leaderClubId = sessionDto.getClubId();

        // セッションが切れた場合、エラー画面に遷移
        if (userId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        /*
         * ➋TODO dtoに値をセット
         * 1.ParticipantListDto型の変数をインスタンス化(new)する。
         * 2.インスタンス化した変数に、パラメータから付与されたactivityIdをセットする(ヒント:setActivityId()を使用する。)
         * 3.インスタンス化した変数に、セッションから取得したuserIdをセットする。
         */
        ParticipantListDto setSessionDto = new ParticipantListDto();
        setSessionDto.setActivityId(activityId);
        setSessionDto.setUserId(userId);

        try {
            // ➌TODO participantListServiceのgetParticipantListDataメソッドを呼び出す。(引数には、❷のTODOが完了した状態のdtoを渡す。)
            ParticipantDto viewData = participantListService.getParticipantListData(setSessionDto);

            // 返却用のリスト
            List<ParticipantListForm> responseListForm = new ArrayList<>();


            /*
             * ➍ TODO responseListFormに値をセット(formをaddする)
             * 1.拡張for文を作成する。(ヒント:for (ParticipantListDto dto :➌の変数.getPariticipantListDto()))
             * 2.ParticipantListForm型の変数をインスタンス化する。
             * 3.formに、activityId,userId,userName,activityNameをセットする。引数にはdtoからgetした変数を指定すること。
             * 4.responseListFormに3でセットを完了させた状態のformをaddする。
             */
            for (ParticipantListDto dto : viewData.getPariticipantListDto()) {
                ParticipantListForm responseForm = new ParticipantListForm();
                responseForm.setActivityId(dto.getActivityId());
                responseForm.setUserId(dto.getUserId());
                responseForm.setUserName(dto.getUserName());
                responseForm.setActivityName(dto.getActivityName());

                // responseListFormにリストを追加
                responseListForm.add(responseForm);
            }
            
            /*
             * ➎ TODO 取得したデータを画面側に渡す。
             * 1.mav(39行目で宣言している変数)に、responseListFormを設定する。(ヒント:mav.addObject("html側で表示したい変数←participant.htmlを参照", responseListForm);)
             * 2.mav(39行目で宣言している変数)に、❸で取得した変数からgetしたactivityNameを設定する。(ヒント:mav.addObject("html側で表示したい変数participant.htmlを参照",○○.getActivityName());)
             */
            mav.addObject("participantListForm", responseListForm);
            mav.addObject("activityName", viewData.getActivityName());

            // messages.propertiesからメッセージを取得
            String resultMessage = messageSource.getMessage("notpariticipant", null, Locale.getDefault());
            mav.addObject("message", resultMessage);
            mav.addObject("leaderClubId", leaderClubId);

            // 遷移先の設定
            mav.setViewName("participantList");
        } catch (Exception e) {
            mav.setViewName("error");
        }

        return mav;

    }

}