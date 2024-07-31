package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jp.co.jc21ps.activity_management.form.RegisterActivityForm;
import jp.co.jc21ps.activity_management.form.RegisterActivitySaveForm;
import jp.co.jc21ps.activity_management.service.RegisterActivityService;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
/*@RequestMapping("/registerActivity")*/
public class RegisterActivityController {

    private final RegisterActivityService registerActivityService;

    //サービスをセット
    public RegisterActivityController(RegisterActivityService registerActivityService) {
        this.registerActivityService = registerActivityService;
    }

    //初期表示
    @GetMapping("/registerActivity")
    public ModelAndView getActivityByClubId(@Valid @PathVariable RegisterActivityForm registerActivityForm, BindingResult bindingResult) { //@PathVariableでリクエストからclubIdを取得(Formごと)

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //formのインスタンス化
        RegisterActivityForm form = new RegisterActivityForm();

        //clubIdのnullチェック,バリデーション
        if (bindingResult.hasErrors()){
            mav.setViewName("Error.html");
        }
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(form.getClubId());

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);
        
        //formにclubNameを渡す
        //formにclubNameをセットする
        form.setClubName(registerActivityDto.getClubName());

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity.html");

        //formオブジェクトを追加
        mav.addObject("form", form);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/RegisterActivitySave")
    //form
    public ModelAndView insertActivity(@Valid @PathVariable RegisterActivitySaveForm registerActivitySaveForm, BindingResult bindingResult){

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //バリデーション
        if (bindingResult.hasErrors()){
            mav.setViewName("RegisterActivity.html");
            
        }

        //値を詰める
        try {
            //dtoのインスタンス化
            RegisterActivitySaveDto activitySaveDto = new RegisterActivitySaveDto();

            //dtoに値を設定
            activitySaveDto.setActivityId(registerActivitySaveForm.getActivityId());
            activitySaveDto.setActivityName(registerActivitySaveForm.getActivityName());
            activitySaveDto.setActivityDate(registerActivitySaveForm.getActivityDate());
            activitySaveDto.setActivityPlace(registerActivitySaveForm.getActivityPlace());
            activitySaveDto.setActivityStartTime(registerActivitySaveForm.getActivityStartTime());
            activitySaveDto.setActivityEndTime(registerActivitySaveForm.getActivityEndTime());
            activitySaveDto.setActivityDescription(registerActivitySaveForm.getActivityDescription());
            activitySaveDto.setMaxParticipant(registerActivitySaveForm.getMaxParticipant());

            //サービスメソッドの呼び出し
            registerActivityService.insertActivity(activitySaveDto);

            //入力が成功したらトップ画面に遷移する
            mav.setViewName("Top.html");

        } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error.html");
        }
        return mav;
    }
}