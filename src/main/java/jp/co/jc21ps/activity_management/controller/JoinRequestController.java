package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
import jp.co.jc21ps.activity_management.form.JoinRequestForm;
import jp.co.jc21ps.activity_management.service.JoinRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequestMapping("/joinRequest")

public class JoinRequestController {
    
    @Autowired
    private final JoinRequestService joinRequestService;

    //サービスをセット
    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    @GetMapping("/dispJoinRequest")
    public ModelAndView getJoinRequestById(@PathVariable String userId, String clubId) {

        //セッションからユーザーID,クラブIDを取得

        //userIdがセッションに存在しない場合、ログイン画面に遷移
        //if(userId == null){
          //mav.setViewName("Login");
          //return mav;
        //}

        //formのインスタンス化
        JoinRequestForm form = 

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //dtoのインスタンス化
        JoinRequestDto joinRequestDto = new JoinRequestDto();

        //dtoにUserId,ClubIdを詰め替える
        joinRequestDto.setUserId(form.getUserId());
        joinRequestDto.setClubId(form.getClubId());

        //サービスのメソッドでデータを取得　
        List<JoinRequestDto> joinRequestList = joinRequestService.findRequest(joinRequestDto);
        //formにリストをつめる
        List<JoinRequestForm> joinRequestForm = new ArrayList<>();

        for(JoinRequestDto dto : joinRequestList){
            JoinRequestForm form = new JoinRequestForm();

            //formに渡すために部署名、部署説明をセットする
            form.setClubName(dto.getClubName());
            form.setClubDescription(dto.getClubDescription());

            joinRequestForm.add(form);
        }

        if(joinRequestList.isEmpty()){
            mav.addObject("message", "申請する部署がありません");
        } else {
            mav.addObject("joinRequestForm", joinRequestForm);
        }

        //Viewの名前を指定する
        mav.setViewName("JoinRequest");

        //formオブジェクトを追加
        mav.addObject("joinRequestForm", form);
        return mav;
    }
}
