package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.jc21ps.activity_management.entity.RegisterActivityEntity;
import jp.co.jc21ps.activity_management.form.RegisterActivityForm;
import jp.co.jc21ps.activity_management.form.RegisterActivitySaveForm;
import jp.co.jc21ps.activity_management.service.RegisterActivityService;
import jp.co.jc21ps.dto.RegisterActivityDto;
import jp.co.jc21ps.dto.RegisterActivitySaveDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
/*@RequestMapping("/registerActivity")*/
public class RegisterActivityController {

    private final RegisterActivityService registerActivityService;

    //サービスをセット
    public RegisterActivityController(RegisterActivityService registerActivityService) {
        this.registerActivityService = registerActivityService;
    }

    //初期表示
    @GetMapping("/RegisterActivity")
    public ModelAndView getActivityByClubId(@PathVariable RegisterActivityForm registerActivityForm) { //@PathVariableでリクエストからclubIdを取得(Formごと)

        //formのインスタンス化
        RegisterActivityForm form = new RegisterActivityForm();
        
        //dtoのインスタンス化
        RegisterActivityDto activityDto = new RegisterActivityDto();

        //dtoにClubIdを詰め替える
        activityDto.setClubId(form.getClubId());

        //サービスのメソッドでデータを取得　※型を合わせる
        RegisterActivityDto registerActivityDto = registerActivityService.findActivityByClubId(activityDto);
        
        //formにclubNameを渡す
        //formにclubnameをセットする
        form.setClubName(registerActivityDto.getClubName());

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //html(View)の名前を指定する
        mav.setViewName("RegisterActivity");

        //formオブジェクトを追加
        mav.addObject("form", form);
        return mav;
    }

    //入力エラー文を返す
    @PostMapping("/RegisterActivitySave")
    //form
    public ModelAndView insertActivity(RegisterActivitySaveForm registerActivitySaveForm){

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //バリデーション

        //値を詰める
        try {
            //dtoのインスタンス化
            RegisterActivitySaveDto activitySaveDto = new RegisterActivitySaveDto();

            //dtoに値を設定
            activitySaveDto.setActivityId(registerActivitySaveForm.getActivityId());
            activitySaveDto.setActivityName(registerActivitySaveForm.getActivityName());
            activitySaveDto.setActivityPlace(registerActivitySaveForm.getActivityPlace());
            activitySaveDto.setActivityStartTime(registerActivitySaveForm.getActivityStartTime());
            activitySaveDto.setActivityEndTime(registerActivitySaveForm.getActivityEndTime());
            activitySaveDto.setActivityDescription(registerActivitySaveForm.getActivityDescription());
            activitySaveDto.setMaxParticipant(registerActivitySaveForm.getMaxParticipant());

            //サービスメソッドの呼び出し
            registerActivityService.insert(activitySaveDto);

            //入力が成功したらトップ画面に遷移する
            mav.setViewName("Top");

        } catch(Exception e) {
            //エラー画面に遷移する
            mav.setViewName("Error");
        }
        return mav;
    }
}

// try{
//     service.insertmethod();
//     画面遷移先(正常)
// }catch{
//     画面遷移先(異常)
// }