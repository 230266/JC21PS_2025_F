package jp.co.jc21ps.activity_management.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import jp.co.jc21ps.activity_management.dto.SessionDto;
import jp.co.jc21ps.activity_management.form.RegisterActivitySaveForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.RegisterActivityService;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registerActivity")
public class RegisterActivityController {

    private final RegisterActivityService registerActivityService;
    private final MessageSource messageSource;
    private final CommonService commonService;

    // サービスをセット,
    public RegisterActivityController(RegisterActivityService registerActivityService, MessageSource messageSource,
            CommonService commonService) {
        this.registerActivityService = registerActivityService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView getActivity(HttpSession session, RegisterActivitySaveForm paramForm) {

        // ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        // セッションからクラブID を取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        // 部長クラブIDがセッションに存在しない場合、エラー画面に遷移
        if (leaderClubId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }
        // formのインスタンス化
        paramForm.setClubId(leaderClubId);

        // dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        // dtoにClubIdを詰め替える
        activityDto.setClubId(leaderClubId);

        // サービスのメソッドでデータを取得 ※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);

        RegisterActivitySaveForm responseForm = new RegisterActivitySaveForm();

        // formに渡すためにclubNameをセットする
        responseForm.setClubName(registerActivityDto.getClubName());

        // html(View)の名前を指定する
        mav.setViewName("registerActivity");

        // formオブジェクトを追加
        mav.addObject("registerActivitySaveForm", responseForm);
        mav.addObject("leaderClubId", leaderClubId);
        return mav;
    }

    // 入力エラー文を返す
    @PostMapping("/save")
    // form
    public ModelAndView insertActivity(@Valid RegisterActivitySaveForm paramForm,
            BindingResult bindingResult, HttpSession session) {

        // ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        // バリデーション
        if (bindingResult.hasErrors()) {
            // List<String> errorMessages = bindingResult.getAllErrors().stream()
            // .map(ObjectError::getDefaultMessage)
            // .collect(Collectors.toList());

            // バリデーションエラーをリストに変換
            mav.addObject("registerActivitySaveForm", paramForm);
            // mav.addObject("errorMessages", errorMessages);
            mav.setViewName("RegisterActivity");
            return mav;
        }

        // セッションからクラブIDを持ってくる
        SessionDto sessionDto = commonService.getSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        // 部長クラブIDがセッションに存在しない場合、エラー画面に遷移
        if (leaderClubId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // 値を詰める
        try {
            // dtoのインスタンス化
            RegisterActivitySaveDto activitySaveDto = new RegisterActivitySaveDto();

            // リポジトリからシーケンスメソッドを持ってくる
            String newActivityId = registerActivityService.getNextActivityId();

            // dtoに値を設定
            activitySaveDto.setActivityId(newActivityId);
            activitySaveDto.setActivityName(paramForm.getActivityName());
            activitySaveDto.setActivityDate(paramForm.getActivityDate());
            activitySaveDto.setActivityPlace(paramForm.getActivityPlace());
            activitySaveDto.setActivityStartTime(paramForm.getActivityStartTime());
            activitySaveDto.setActivityEndTime(paramForm.getActivityEndTime());
            activitySaveDto.setActivityDescription(paramForm.getActivityDescription());
            activitySaveDto.setMaxParticipant(paramForm.getMaxParticipant());
            activitySaveDto.setClubId(leaderClubId);

            // サービスメソッドの呼び出し
            String resultMessageKey = registerActivityService.insertActivity(activitySaveDto);

            // メッセージを取得
            String resultMessage = messageSource.getMessage(resultMessageKey, null, Locale.getDefault());

            // 入力が成功したら、トップ画面に遷移する → メッセージ表示
            switch (resultMessageKey) {
                case "activityRegisterCompleteMessage":
                    mav.addObject("activityRegisterCompleteMessage", resultMessage);
                    mav.addObject("leaderClubId", leaderClubId);
                    mav.setViewName("redirect:/top");
                    break;

                case "impossibleDate":
                    mav.addObject("impossibleDate", resultMessage);
                    mav.addObject("leaderClubId", leaderClubId);
                    mav.setViewName("RegisterActivity");
                    break;
                default:
                    mav.setViewName("error");
            }
            // DB接続に失敗した場合、エラー画面に遷移する
        } catch (Exception e) {
            // エラー画面に遷移する
            mav.setViewName("error");
        }
        return mav;
    }
}