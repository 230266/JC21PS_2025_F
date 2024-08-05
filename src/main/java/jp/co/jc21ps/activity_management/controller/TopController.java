package jp.co.jc21ps.activity_management.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.TopDto;
import jp.co.jc21ps.activity_management.entity.TopEntity;
import jp.co.jc21ps.activity_management.form.TopForm;
import jp.co.jc21ps.activity_management.service.TopService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
        TopForm actList = new TopForm();

        for(TopDto lastForm : viewAct){
            actList.setNo(lastForm.getNo());
            actList.setClubId(lastForm.getClubId());
            actList.setClubName(lastForm.getClubName());
            actList.setActivityId(lastForm.getActivityId());
            actList.setActivityName(lastForm.getActivityName());
            actList.setActivityPlace(lastForm.getActivityPlace());
            actList.setDispActivityDate(lastForm.getDispActivityDate());
            actList.setDispActivityTime(lastForm.getDispActivityTime());
            actList.setActivityStartTime(lastForm.getActivityStartTime());
            actList.setActivityEndTime(lastForm.getActivityEndTime());
            actList.setActivityDescription(lastForm.getActivityDescription());
            actList.setParticipantsCount(lastForm.getParticipantsCount());
            actList.setMaxParticipant(lastForm.getMaxParticipant());
            actList.setIsParticipationFlg(lastForm.getIsParticipationFlg());
            actList.setIsMajorityFlg(lastForm.getIsMajorityFlg());
            
        }
        ModelAndView mav = new ModelAndView();
        if(viewAct.isEmpty()){
            mav.addObject("message","活動予定はありません。");
        }else{
            mav.addObject("topform",actList);
        }

        mav.setViewName("/top.html");
        
        return mav;
    }

    //@PostMapping("path")
    //ModelAndView postResult(TopForm topForm )
    
    
}
