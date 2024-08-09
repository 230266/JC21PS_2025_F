package jp.co.jc21ps.activity_management.controller;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.jc21ps.activity_management.dto.TopDataDto;
import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.entity.TopDataEntity;
import jp.co.jc21ps.activity_management.form.TopForm;
import jp.co.jc21ps.activity_management.form.TopDataForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.TopService;
import jp.co.jc21ps.dto.SessionDto;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/top")
public class TopController {

    private final TopService topService;
    private final CommonService commonService;

    public TopController(TopService topService,CommonService commonService) {
        this.topService = topService;
        this.commonService = commonService;
    }
    
    
    @PostMapping("/save")
    public ModelAndView toggleParticipation(TopDataForm topDataForm) {
        
        //Dtoを呼び出し、dtoにFormから活動IDとユーザーIDを詰め込む
        TopDataDto topDataDto = new TopDataDto();
        topDataDto.setActivityId(topDataForm.getActivityId());
        topDataDto.setUserId(topDataForm.getUserId());
        
        //参加している場合は削除、参加していない場合は追加
        //try{

        // アクティビティ参加状況を取得するメソッドを呼び出す
        boolean isParticipating = topService.getActivityParticipationStatus(topDataDto);

    

        //参加していたらfalse,参加していなかったらtrueになる
        if (isParticipating) {
            
            topService.deleteActivity(topDataDto);
        } else {
            topService.insertActivity(topDataDto);
        }

        ModelAndView mav = new ModelAndView();
            //取得できた場合はトップ画面に遷移
        mav.setViewName("redirect:/top");
        //}catch(Exception e){
            //取得できなかった場合はエラー画面に遷移
        //    mav.setViewName("redirect:/error");
        //}

    return mav;
    }
  
    @GetMapping
    public ModelAndView top(HttpSession session,Model model) {
        
        //try{
            // セッションから値を取得する
            SessionDto sessionDto = new SessionDto();
            
            //CommonServiceという別クラスを作り、そこからDto経由でセッションを持ってきている
            sessionDto = commonService.getCommonService(session);
            String userId =  sessionDto.getUserId();
            
            
            //if(userId.isEmpty()){
            //    //userIdがnullまたはからの場合はエラーページに遷移
            //    mav.setViewName("redirect:/error");
            //     return mav;
            //}
            
            //userIdをもとにデータを取得する
            TopDto topDto = new TopDto();
            topDto.setUserId(userId);
            List<TopDto> viewAct = topService.getTopData(topDto);
            
            List<TopForm> actList = new ArrayList<>();

            for(TopDto lastForm : viewAct){
                //TopForm型のactに値を詰めていく
                TopForm act = new TopForm();
                act.setNo(lastForm.getNo());
                act.setClubId(lastForm.getClubId());
                act.setClubName(lastForm.getClubName());
                act.setActivityId(lastForm.getActivityId());
                act.setActivityName(lastForm.getActivityName());
                act.setActivityPlace(lastForm.getActivityPlace());
                act.setDispActivityDate(lastForm.getDispActivityDate());
                act.setDispActivityTime(lastForm.getDispActivityTime());
                act.setActivityStartTime(lastForm.getActivityStartTime());
                act.setActivityEndTime(lastForm.getActivityEndTime());
                act.setActivityDescription(lastForm.getActivityDescription());
                act.setParticipantsCount(lastForm.getParticipantsCount());
                act.setMaxParticipant(lastForm.getMaxParticipant());
                act.setIsParticipationFlg(lastForm.getIsParticipationFlg());
                act.setIsMajorityFlg(lastForm.getIsMajorityFlg());

            actList.add(act); 
                
            }
            ModelAndView mav = new ModelAndView();
            if(viewAct.isEmpty()){
                mav.addObject("message","活動予定はありません。");
            }else{
                mav.addObject("topform",actList);
            }
            mav.setViewName("top");

        //}catch(Exception e) {
            //問題が発生した場合はエラーページにリダイレクト
        //    mav.setViewName("redirect:/error"); 
        //}
        
        return mav;
    }
}
