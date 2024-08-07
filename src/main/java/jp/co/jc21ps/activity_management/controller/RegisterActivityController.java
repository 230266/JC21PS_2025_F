package jp.co.jc21ps.activity_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final RegisterActivityService registerActivityService;

    //サービスをセット,
    public RegisterActivityController(RegisterActivityService registerActivityService) {
        this.registerActivityService = registerActivityService;
    }

    @GetMapping("/disp/{clubId}") ///registerActivity
    public ModelAndView getActivityByClubId(@PathVariable String clubId, RegisterActivityForm registerActivityForm) { //@PathVariableでリクエストからclubIdを取得(Formごと)
        // public ModelAndView getActivityByClubId(@PathVariable String clubId, @Valid RegisterActivityForm registerActivityForm, BindingResult bindingResult) {
        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //セッションから部長クラブID を取得
       //String leaderClubId = (String) session.getAttribute("clubId");

        //部長クラブIDがセッションに存在しない場合、ログイン画面に遷移
        // if(leaderClubId == null) {
            //  mav.setViewName("Login");
            //  return mav;
        // }

        //formのインスタンス化
        // RegisterActivityForm form = new RegisterActivityForm();
        registerActivityForm.setClubId("C001");

        //clubIdのnullチェック,バリデーション
        // if (bindingResult.hasErrors()){
        //      mav.setViewName("Error.html");
        //      return mav;
        // }
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(registerActivityForm.getClubId());
        

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivity(activityDto);
        
        //formに渡すためにclubNameをセットする
        registerActivityForm.setClubName(registerActivityDto.getClubName());

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity.html");

        //formオブジェクトを追加
        mav.addObject("registerActivityForm", registerActivityForm);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/save")
    //form
    public ModelAndView insertActivity(@Valid RegisterActivitySaveForm registerActivitySaveForm, BindingResult bindingResult){
        //セッション処理

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //バリデーション
        // if (bindingResult.hasErrors()){
        //     mav.addObject("errors", bindingResult.getAllErrors());
        //     mav.setViewName("/RegisterActivity.html");
        //     return mav;
        // }

        if (bindingResult.hasErrors()) {
            ModelAndView mave = new ModelAndView("RegisterActivity");
            mave.addObject("registerActivitySaveForm", registerActivitySaveForm);
            mave.addObject("errors", bindingResult.getAllErrors());
            return mave;
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
            String resultMessage = registerActivityService.insertActivity(activitySaveDto);
            //registerActivityService.insertActivity(activitySaveDto);

            //活動日に存在しない日時が入力されたらエラー文を表示
            if(resultMessage.contains("エラー")){
              mav.addObject("impossibleDate", resultMessage);
              mav.setViewName("RegisterActivity.html");
            }else{
               //入力が成功したら、トップ画面に遷移する → メッセージ表示
               mav.addObject("activityRegisterCompleteMessage", resultMessage);
               mav.setViewName("Top");
            }
        } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error");
        }
        return mav;
    }
}