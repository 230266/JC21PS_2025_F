package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.jc21ps.activity_management.dto.ParticipantListDto;
import jp.co.jc21ps.activity_management.form.ParticipantListForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.ParticipantListService;
import jp.co.jc21ps.dto.ParticipantDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/ParticipantList")
public class ParticipantListController {
    private final ParticipantListService participantListService;
    private final CommonService commonService;

    public ParticipantListController(ParticipantListService participantListService,CommonService commonService) {
       this.participantListService = participantListService;
       this.commonService = commonService;
    }
    

    @GetMapping
    public ModelAndView ParticipantList(@RequestParam(value = "activityId", required = false) String activityId,HttpSession session) {
            ModelAndView mav = new ModelAndView();
        //try{
            //if (activityId.isEmpty()) {
            //mav.setViewName("redirect:/error");
            //return mav;
        //}


            //セッションから値を取得する
            SessionDto sessionDto = new SessionDto();
            //CommonServiceという別クラスを作り、そこからDto経由でセッションを持ってきている
            sessionDto = commonService.getCommSessionDto(session);
            String userId =  sessionDto.getUserId();

            //if(userId.isEmpty()){
            //    //userIdがnullまたはからの場合はエラーページに遷移
            //    mav.setViewName("redirect:/error");
            //    return mav;
            //}

            //userIdをもとにデータを取得する
            ParticipantListDto participantListDto = new ParticipantListDto();
            participantListDto.setActivityId(activityId);
            participantListDto.setUserId(userId);
            ParticipantDto viewList = participantListService.getListData(participantListDto);
            
            List<ParticipantListForm> participantList = new ArrayList<>();
          

            for(ParticipantListDto form : viewList.getPariticipantDto()){
                ParticipantListForm list = new ParticipantListForm();
                list.setActivityId(form.getActivityId());
                list.setUserId(form.getUserId());
                list.setUserName(form.getUserName());
                list.setActivityName(form.getActivityName());

                participantList.add(list);
            }

        
            
            if(participantList.isEmpty()){
                mav.addObject("activityName", viewList.getActivityName());
                mav.addObject("message","参加者はいません。");
            }else{
                mav.addObject("participantListForm",participantList);
                mav.addObject("activityName", participantList.get(0).getActivityName()); 
            }
            mav.setViewName("ParticipantList");
        //}catch(Exception e){
            //問題が発生した場合はエラーページにリダイレクト・
        //    mav.setViewName("redirect:/error"); 
        //}
        return mav;
        //}
    
        //}
    }
}