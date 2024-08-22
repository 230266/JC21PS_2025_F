package jp.co.jc21ps.activity_management.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import jp.co.jc21ps.activity_management.form.RegisterActivitySaveForm;
import jp.co.jc21ps.activity_management.service.RegisterActivityService;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registerActivity")
public class RegisterActivityController {

    @Autowired
    private final RegisterActivityService registerActivityService;
    @Autowired
    private final MessageSource messageSource;

    //サービスをセット,
    public RegisterActivityController(RegisterActivityService registerActivityService, MessageSource messageSource) {
        this.registerActivityService = registerActivityService;
        this.messageSource = messageSource;
    }

    @GetMapping("/disp/{clubId}") ///registerActivity
    public ModelAndView getActivityByClubId(@PathVariable String clubId) { //@PathVariableでリクエストからclubIdを取得(Formごと)
        // public ModelAndView getActivityByClubId(@PathVariable String clubId, @Valid RegisterActivityForm registerActivityForm, BindingResult bindingResult) {
        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //セッションからクラブID を取得
       //String leaderClubId = (String) session.getAttribute("clubId");

        //部長クラブIDがセッションに存在しない場合、ログイン画面に遷移
        // if(leaderClubId == null) {
            //  mav.setViewName("Login");
            //  return mav;
        // }

        //formのインスタンス化
        RegisterActivitySaveForm form = new RegisterActivitySaveForm();
        form.setClubId("C001");


        //clubIdのnullチェック,バリデーション
        // if (bindingResult.hasErrors()){
        //      mav.setViewName("Error.html");
        //      return mav;
        // }
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(form.getClubId());
        

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);
        
        //formに渡すためにclubNameをセットする
        form.setClubName(registerActivityDto.getClubName());

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity");

        //formオブジェクトを追加
        mav.addObject("registerActivitySaveForm", form);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/save")
    //form
    public ModelAndView insertActivity(@Valid RegisterActivitySaveForm registerActivitySaveForm, BindingResult bindingResult) {

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
            //activitySaveDto.setClubId(registerActivitySaveForm.getClubId());

            activitySaveDto.setClubId("C001");

            //サービスメソッドの呼び出し 
            String resultMessageKey = registerActivityService.insertActivity(activitySaveDto);
            //registerActivityService.insertActivity(activitySaveDto);

            //メッセージを取得
            String resultMessage = messageSource.getMessage(resultMessageKey, null, Locale.getDefault());

            //入力が成功したら、トップ画面に遷移する → メッセージ表示
            switch(resultMessageKey) {
                case "activityRegisterCompleteMessage":
                    mav.addObject("activityRegisterCompleteMessage", resultMessage);
                    mav.setViewName("Top");
                break;

                case "impossibleDate":
                    mav.addObject("impossibleDate", resultMessage);
                    mav.setViewName("RegisterActivity");
                break;
                default:
                    mav.setViewName("Error");
            }

            // if(resultMessage.contains("{activityRegisterCompleteMessage}")) {
            //     mav.addObject("activityRegisterCompleteMessage", resultMessage);
            //     mav.setViewName("Top");
            // }else{
            //    //活動日に存在しない日時が入力されたらエラー文を表示
            //    mav.addObject("activityDate", resultMessage);
            //    mav.setViewName("RegisterActivity");
            // }
        } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error");
        }
        return mav;
    }
}



//clubanmeをformで送る
//Asserttrueのバリデーション