package jp.co.jc21ps.activity_management.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    //サービスをセット,
    public RegisterActivityController(RegisterActivityService registerActivityService, MessageSource messageSource, CommonService commonService) {
        this.registerActivityService = registerActivityService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView getActivityByClubId(HttpSession session, RegisterActivitySaveForm  registerActivitySaveForm) { 

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //セッションからクラブID を取得
        SessionDto sessionDto = commonService.getCommSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        //部長クラブIDがセッションに存在しない場合、ログイン画面に遷移
        if(leaderClubId == null) {
             mav.setViewName("Login");
             return mav;
        }

        //formのインスタンス化
        registerActivitySaveForm.setClubId(leaderClubId);

        //clubIdのnullチェック,バリデーション
        // if (bindingResult.hasErrors()){
        //      mav.setViewName("Error.html");
        //      return mav;
        // }
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(leaderClubId);
        

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);
        
        //formに渡すためにclubNameをセットする
        registerActivitySaveForm.setClubName(registerActivityDto.getClubName());

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity");

        //formオブジェクトを追加
        mav.addObject("registerActivitySaveForm", registerActivitySaveForm);
        mav.addObject("leaderClubId", leaderClubId);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/save")
    //form
    public ModelAndView insertActivity(@Valid RegisterActivitySaveForm registerActivitySaveForm, BindingResult bindingResult, HttpSession session) {

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //バリデーション
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());

            //バリデーションエラーをリストに変換
            mav.addObject("registerActivitySaveForm", registerActivitySaveForm);
            mav.addObject("errorMessages", errorMessages);
            mav.setViewName("RegisterActivity");
            return mav;
        }

        //セッションからクラブIDを持ってくる
        SessionDto sessionDto = commonService.getCommSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        //値を詰める
        try {
            //dtoのインスタンス化
            RegisterActivitySaveDto activitySaveDto = new RegisterActivitySaveDto();

            //リポジトリからシーケンスメソッドを持ってくる
            String newActivityId = registerActivityService.getNextActivityId();

            //dtoに値を設定
            activitySaveDto.setActivityId(newActivityId);
            activitySaveDto.setActivityName(registerActivitySaveForm.getActivityName());
            activitySaveDto.setActivityDate(registerActivitySaveForm.getActivityDate());
            activitySaveDto.setActivityPlace(registerActivitySaveForm.getActivityPlace());
            activitySaveDto.setActivityStartTime(registerActivitySaveForm.getActivityStartTime());
            activitySaveDto.setActivityEndTime(registerActivitySaveForm.getActivityEndTime());
            activitySaveDto.setActivityDescription(registerActivitySaveForm.getActivityDescription());
            activitySaveDto.setMaxParticipant(registerActivitySaveForm.getMaxParticipant());
            activitySaveDto.setClubId(registerActivitySaveForm.getClubId());

            //サービスメソッドの呼び出し 
            String resultMessageKey = registerActivityService.insertActivity(activitySaveDto);
            //registerActivityService.insertActivity(activitySaveDto);

            //メッセージを取得
            String resultMessage = messageSource.getMessage(resultMessageKey, null, Locale.getDefault());

            //入力が成功したら、トップ画面に遷移する → メッセージ表示
            switch(resultMessageKey) {
                case "activityRegisterCompleteMessage":
                    mav.addObject("activityRegisterCompleteMessage", resultMessage);
                    mav.addObject("leaderClubId", leaderClubId);
                    mav.setViewName("Top");
                break;

                case "impossibleDate":
                    mav.addObject("impossibleDate", resultMessage);
                    mav.addObject("leaderClubId", leaderClubId);
                    mav.setViewName("RegisterActivity");
                break;
                default:
                    mav.setViewName("Error");
            }
        } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error");
        }
        return mav;
    }
}



//clubanmeをformで送る
//Asserttrueのバリデーション