package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
import jp.co.jc21ps.activity_management.dto.JoinRequestSaveDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import jp.co.jc21ps.activity_management.form.JoinRequestSaveForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.JoinRequestService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/joinRequest")

public class JoinRequestController {

    private final JoinRequestService joinRequestService;
    private final MessageSource messageSource;
    private final CommonService commonService;

    // サービスをセット
    public JoinRequestController(JoinRequestService joinRequestService, MessageSource messageSource,
            CommonService commonService) {
        this.joinRequestService = joinRequestService;
        this.messageSource = messageSource;
        this.commonService = commonService;
    }

    @GetMapping
    public ModelAndView getJoinRequestById(HttpSession session, JoinRequestSaveForm paramForm,
            @ModelAttribute("joinOkMessage") String joinOkMessage) {

        ModelAndView mav = new ModelAndView();

        // セッションからuserIdを取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String userId = sessionDto.getUserId();
        String leaderClubId = sessionDto.getClubId();

        // セッションが切れた場合、エラー画面に遷移
        if (userId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // formに値をセット
        JoinRequestSaveForm form = new JoinRequestSaveForm();
        form.setUserId(userId);

        // dtoに値をセット
        JoinRequestDto joinRequestDto = new JoinRequestDto();
        joinRequestDto.setUserId(userId);

        List<JoinRequestDto> joinRequestList = joinRequestService.findRequest(joinRequestDto);
        List<JoinRequestSaveForm> responseForm = new ArrayList<>();

        // formに値をセット
        for (JoinRequestDto dto : joinRequestList) {

            JoinRequestSaveForm saveData = new JoinRequestSaveForm();
            saveData.setClubName(dto.getClubName());
            saveData.setClubDescription(dto.getClubDescription());
            saveData.setClubId(dto.getClubId());

            // responseFormにリストを追加
            responseForm.add(saveData);

        }
        // リダイレクトされてきた登録申請成功のメッセージを、paramFormにセットする
        paramForm.setMessage(joinOkMessage);


        /*
         * TODO ➊ 初期表示情報取得結果(これより上のどこかの処理で格納している)に応じて、以下の条件文を完成させる。
         * 1.if(結果が存在しないとき)
         * ├ この処理は使用してください。→String notRequestClubMessage = messageSource.getMessage("notRequestClubMessage", null, Locale.getDefault());
         * ├ notRequestClubMessageをmavに設定(ヒント : mav.addObject("html側で表示したい箇所→joinRequest.htmlを参照する。", notRequestClubMessage);)
         * └ if(パラメータが存在しているとき = 申請情報が送られたとき) (ヒント : if (!ObjectUtils.isEmpty(paramForm)))
         *    └ mavに成功メッセージを設定する(ヒント:mav.addObject("html側で表示したい変数", paramForm.get○○());
         * 2.else(=結果が存在するとき)
         * ├ if(パラメータが存在しているとき = 申請情報が送られたとき) (ヒント : if (!ObjectUtils.isEmpty(paramForm)))
         *    └ mavに成功メッセージを設定する(ヒント:mav.addObject("html側で表示したい変数", paramForm.get○○());
         * └ mav(44行目で宣言)に初期表示情報を設定する(ヒント:mav.addObject("joinRequestSaveForm", 取得結果が格納されている変数);
         */
        if (responseForm.isEmpty()) {

            // messages.propertiesからメッセージを取得
            String notRequestClubMessage = messageSource.getMessage("notRequestClubMessage", null, Locale.getDefault());

            // 申請する部署がない場合のメッセージ
            mav.addObject("notRequestClubMessage", notRequestClubMessage);

            // 部員登録成功メッセージ
            if (!ObjectUtils.isEmpty(paramForm)) {
                mav.addObject("joinRequestCompleteMessage", paramForm.getMessage());
            }
        } else {
            if (!ObjectUtils.isEmpty(paramForm)) {
                mav.addObject("joinRequestCompleteMessage", paramForm.getMessage());
            }
            mav.addObject("joinRequestSaveForm", responseForm);
        }
        mav.addObject("leaderClubId", leaderClubId);

        // 部員登録申請画面に遷移
        mav.setViewName("joinRequest");
        return mav;

    }

    // インサート処理
    @PostMapping("/save")
    public ModelAndView insertRequestClub(HttpSession session, JoinRequestSaveForm paramForm,
            RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();

        // セッションからuserIdを取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String userId = sessionDto.getUserId();

        // セッションが切れた場合、エラー画面に遷移
        if (userId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // dtoに値をセット
        JoinRequestSaveDto joinRequestSaveDto = new JoinRequestSaveDto();
        joinRequestSaveDto.setUserId(userId);
        joinRequestSaveDto.setClubId(paramForm.getClubId());

        try {
            /*
            * TODO ➋ インサートの成功、失敗に応じて、処理を変更する。
            * 1.joinRequestServiceから、insertJoinRequestメソッドを呼び出す。(引数にはパラメータの情報を格納する。)
            * 2.if(Trueの場合)
            *   ├ この処理は使用してください。String joinRequestCompleteMessage = messageSource.getMessage("joinRequestCompleteMessage", null, Locale.getDefault());
            *   ├ paramFormにjoinRequestCompleteMessageをセットする。
            *   ├ リダイレクト先に成功メッセージを渡す。(ヒント:redirectAttributes.addFlashAttribute("41-42行目の引数のどれか", paramForm.getMessage());)
            *   └ リダイレクト先を設定する。(ヒント:mav.setViewName("redirect:/リダイレクト先");
            * 3.else
            * 　└ 遷移先をエラー画面に設定する
            */
            boolean result = joinRequestService.insertJoinRequest(joinRequestSaveDto);

            if (result) {
                // messages.propertiesからメッセージを取得
                String joinRequestCompleteMessage = messageSource
                        .getMessage("joinRequestCompleteMessage", null, Locale.getDefault());
                paramForm.setMessage(joinRequestCompleteMessage);

                // リダイレクト先に登録申請成功メッセージを渡す
                redirectAttributes.addFlashAttribute("joinOkMessage", paramForm.getMessage());
                mav.setViewName("redirect:/joinRequest");

            } else {
                // 登録失敗した場合、エラー画面に遷移
                mav.setViewName("error");
            }
        } catch (Exception e) {
            mav.setViewName("error");
        }
        return mav;
    }
}
