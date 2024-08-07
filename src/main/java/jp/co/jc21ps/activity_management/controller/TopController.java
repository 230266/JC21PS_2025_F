package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.TopDto;
//import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.form.TopForm;
import jp.co.jc21ps.activity_management.service.TopService;


//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/top")
public class TopController {

    private final TopService topService;

    public TopController(TopService topService) {
        this.topService = topService;
    }
    
    @GetMapping
    public ModelAndView top(HttpSession session,Model model) {
         // セッションから値を取得する
        String userId = (String) session.getAttribute("userId");
        TopDto topDto = new TopDto();
         topDto.setUserId(userId);
        List<TopDto> viewAct = topService.getTopData(topDto);
        
        List<TopForm> actList = new ArrayList<>();

        for(TopDto lastForm : viewAct){
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

        mav.setViewName("/Top.html");
        
        return mav;
    }

    //@PostMapping("path")
    //ModelAndView postResult(TopForm topForm )
    
    
}
