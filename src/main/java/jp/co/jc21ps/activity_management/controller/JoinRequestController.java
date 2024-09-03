package jp.co.jc21ps.activity_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
import jp.co.jc21ps.activity_management.dto.JoinRequestSaveDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import jp.co.jc21ps.activity_management.form.JoinRequestSaveForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import jp.co.jc21ps.activity_management.service.JoinRequestService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/joinRequest")

public class JoinRequestController {
    
    private final JoinRequestService joinRequestService;
    private final MessageSource messageSource;
    private final CommonService commonService;

    //サービスをセット
    public JoinRequestController(JoinRequestService joinRequestService, MessageSource messageSource, CommonService commonService) {
        this.joinRequestService = joinRequestService;
        this.messageSource = messageSource;
        this.commonService = commonService;
    }

    @GetMapping
    public ModelAndView getJoinRequestById(HttpSession session, JoinRequestSaveForm joinRequestSaveForm, @ModelAttribute("joinOkMessage") String joinOkMessage) {

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        //セッションからユーザーIDを取得
        SessionDto sessionDto = new SessionDto();

        sessionDto = commonService.getCommonService(session);
        String userId = sessionDto.getUserId();
        String leaderClubId = sessionDto.getClubId();

        //userIdがセッションに存在しない場合、ログイン画面に遷移
        if(userId == null){
            mav.setViewName("Login");
            return mav;
        } 
        
        //formのインスタンス化
        JoinRequestSaveForm form = new JoinRequestSaveForm();
        form.setUserId(userId);

        //dtoのインスタンス化
        JoinRequestDto joinRequestDto = new JoinRequestDto();

        //dtoにUserId,ClubIdを詰め替える
        joinRequestDto.setUserId(userId);

        //サービスのメソッドでデータを取得　
        List<JoinRequestDto> joinRequestList = joinRequestService.findRequest(joinRequestDto);
        //formにリストをつめる
        List<JoinRequestSaveForm> joinRequestSaveFormList = new ArrayList<>();

        for(JoinRequestDto dto : joinRequestList){
            JoinRequestSaveForm joinForm = new JoinRequestSaveForm();

            //formに渡すために部署名、部署説明をセットする
            joinForm.setClubName(dto.getClubName());
            joinForm.setClubDescription(dto.getClubDescription());
            joinForm.setClubId(dto.getClubId());
            joinRequestSaveFormList.add(joinForm);
        }
        joinRequestSaveForm.setMessage(joinOkMessage);
        // 引数のjoinRequestSaveFormからメッセージを取得、addObjectにセットする.if文どっちも
        if(joinRequestSaveFormList.isEmpty()){
            //メッセージプロパティーズから
            String notRequestClubMessage = messageSource.getMessage("notRequestClubMessage", null, Locale.getDefault());
            mav.addObject("notRequestClubMessage", notRequestClubMessage);
            if(!ObjectUtils.isEmpty(joinRequestSaveForm)){
                mav.addObject("joinRequestCompleteMessage", joinRequestSaveForm.getMessage());
            }
        } else {
            if(!ObjectUtils.isEmpty(joinRequestSaveForm)){
                mav.addObject("joinRequestCompleteMessage", joinRequestSaveForm.getMessage());
            } 
            mav.addObject("leaderClubId", leaderClubId);
            mav.addObject("joinRequestSaveForm", joinRequestSaveFormList);
        }

        //Viewの名前を指定する
        mav.setViewName("JoinRequest");
        return mav;
    }

    //インサート処理
    @PostMapping("/save")
    public ModelAndView insertRequestClub(HttpSession session, JoinRequestSaveForm joinRequestSaveForm, RedirectAttributes redirectAttributes) {

        //ModelAndViewのインスタンス化
        ModelAndView mav = new ModelAndView();

        // //セッションからユーザーIDを取得
        SessionDto sessionDto = new SessionDto();

        sessionDto = commonService.getCommonService(session);
        String userId = sessionDto.getUserId();
        // //String leaderClubId = sessionDto.getClubId();

        // //userIdがセッションに存在しない場合、ログイン画面に遷移
        // if(userId == null){
        //     mav.setViewName("Login");
        //     return mav;
        // } 

        //dtoをnew
        JoinRequestSaveDto joinRequestSaveDto = new JoinRequestSaveDto();
        joinRequestSaveDto.setUserId(userId);
        //joinRequestSaveDto.setUserId("00000028");
        joinRequestSaveDto.setClubId(joinRequestSaveForm.getClubId());

        //delete_flgを呼び出す
        boolean result = joinRequestService.insertJoinRequest(joinRequestSaveDto);

        // //インサート成功したら部員登録申請画面へリダイレクト
        if (result) {
            //メッセージを取得
            String joinRequestCompleteMessage = messageSource.getMessage("joinRequestCompleteMessage", null, Locale.getDefault());
            
            //下を追加する,formにメッセージをセットする
            joinRequestSaveForm.setMessage(joinRequestCompleteMessage);
            redirectAttributes.addFlashAttribute("joinOkMessage", joinRequestSaveForm.getMessage());
            //mav.addObject("joinRequestSaveForm", joinRequestSaveForm);
            mav.setViewName("redirect:/joinRequest");

        //インサート失敗したらエラー画面へリダイレクト
        } else {
            mav.setViewName("Error");
        }
        return mav;
    }
}



//これで申請したのを復元できる
// DELETE FROM trn_join_request
// WHERE user_id = '00000002' AND club_id = 'C001';