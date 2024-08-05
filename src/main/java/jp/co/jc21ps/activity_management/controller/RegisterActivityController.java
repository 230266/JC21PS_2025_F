package jp.co.jc21ps.activity_management.controller;

import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registerActivity")
public class RegisterActivityController {

    private final RegisterActivityService registerActivityService;

    //サービスをセット
    public RegisterActivityController(RegisterActivityService registerActivityService) {
        this.registerActivityService = registerActivityService;
    }

    //初期表示
    @GetMapping("/disp/{clubId}") ///registerActivity
    public ModelAndView getActivityByClubId(@PathVariable String clubId, @Valid RegisterActivityForm registerActivityForm, BindingResult bindingResult /*HttpSession session*/) { //@PathVariableでリクエストからclubIdを取得(Formごと)

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //セッションから部長クラブID を取得
        /*String leaderClubId = (String) session.getAttribute("leaderClubId");

        //部長クラブIDがセッションに存在しない場合、ログイン画面に遷移
         if(leaderClubId == null) {
             mav.setViewName("Login");
             return mav;
        }*/

        //formのインスタンス化
        RegisterActivityForm form = new RegisterActivityForm();
        form.setClubId("C001");

        //clubIdのnullチェック,バリデーション
        if (bindingResult.hasErrors()){
            mav.setViewName("Error.html");
            return mav;
        }
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(form.getClubId());
        

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);
        
        //formに渡すためにclubNameをセットする
        form.setClubName(registerActivityDto.getClubName());

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity.html");

        //formオブジェクトを追加
        mav.addObject("form", form);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/save")
    //form
    public ModelAndView insertActivity(RegisterActivitySaveForm registerActivitySaveForm, BindingResult bindingResult){
        //セッション処理

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //formのインスタンス化
        // RegisterActivitySaveForm SaveForm = new RegisterActivitySaveForm();
        
        //バリデーション
        //if (bindingResult.hasErrors()){
          // mav.setViewName("RegisterActivity");
           //return mav;
        //}

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
           String resultMessage = registerActivityService.insertActivity(activitySaveDto);

            //活動日に存在しない日時が入力されたらエラー文を表示
            if(resultMessage.contains("エラー")){
              mav.addObject("impossibleDateTime", resultMessage);
              mav.setViewName("RegisterActivity.html");
            }else{
              //入力が成功したら、トップ画面に遷移する → メッセージ表示
              mav.addObject("message", resultMessage);
              mav.setViewName("Top");
            }
       } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error");
            return mav;
        }
        return mav;
    }
}