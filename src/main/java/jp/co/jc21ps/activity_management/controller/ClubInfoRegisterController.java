package jp.co.jc21ps.activity_management.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.jc21ps.activity_management.dto.ClubInfoRegisterDto;
import jp.co.jc21ps.activity_management.dto.SessionDto;
import jp.co.jc21ps.activity_management.form.ClubInfoRegisterSaveForm;
import jp.co.jc21ps.activity_management.service.ClubInfoRegisterService;
import jp.co.jc21ps.activity_management.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/clubInfoRegister")

public class ClubInfoRegisterController {
    private final ClubInfoRegisterService clubInfoRegisterService;
    private final CommonService commonService;
    private final MessageSource messageSource;

    public ClubInfoRegisterController(ClubInfoRegisterService clubInfoRegisterService, MessageSource messageSource,
            CommonService commonService) {
        this.clubInfoRegisterService = clubInfoRegisterService;
        this.commonService = commonService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView getClubInfoByClubId(HttpSession session, ClubInfoRegisterSaveForm clubInfoRegisterSaveForm) {

        ModelAndView mav = new ModelAndView();

        // セッションからクラブIDを取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        // leaderClubIdがセッションに存在しない場合、エラー画面に遷移
        if (leaderClubId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        // formをnew
        clubInfoRegisterSaveForm.setLeaderClubId(leaderClubId);

        // dtoをnew
        ClubInfoRegisterDto dto = new ClubInfoRegisterDto();

        // dtoにleaderClubIdを詰め替える
        dto.setLeaderClubId(leaderClubId);

        // サービスのメソッドでデータを取得
        ClubInfoRegisterDto clubInfoRegisterDto = clubInfoRegisterService.findClubInfo(dto);

        // formにclubName,clubDescription,LeaderClubIdをセットする
        clubInfoRegisterSaveForm.setClubName(clubInfoRegisterDto.getClubName());
        clubInfoRegisterSaveForm.setClubDescription(clubInfoRegisterDto.getClubDescription());
        clubInfoRegisterSaveForm.setLeaderClubId(clubInfoRegisterDto.getLeaderClubId());

        // leaderClubId,formをmavにつめる
        mav.addObject("leaderClubId", leaderClubId);
        mav.addObject("clubInfoRegisterSaveForm", clubInfoRegisterSaveForm);

        // viewを指定
        mav.setViewName("clubInfoRegister");
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView updateClubInfo(@Valid ClubInfoRegisterSaveForm clubInfoRegisterSaveForm,
            BindingResult bindingResult, HttpSession session) {

        ModelAndView mav = new ModelAndView();

        // バリデーション
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            mav.addObject("clubInfoRegisterSaveForm", clubInfoRegisterSaveForm);
            mav.addObject("errorMessages", errorMessages);
            mav.setViewName("ClubInfoRegister");
            return mav;
        }

        // セッションからleaderClubIdを取得
        SessionDto sessionDto = commonService.getSessionDto(session);
        String leaderClubId = sessionDto.getClubId();

        // cleaderClubIdlubIdがセッションに存在しない場合、エラー画面に遷移
        if (leaderClubId.isEmpty()) {
            mav.setViewName("error");
            return mav;
        }

        try {
            ClubInfoRegisterDto clubInfoRegisterDto = new ClubInfoRegisterDto();
            clubInfoRegisterDto.setLeaderClubId(clubInfoRegisterSaveForm.getLeaderClubId());
            clubInfoRegisterDto.setClubDescription(clubInfoRegisterSaveForm.getClubDescription());

            String result = clubInfoRegisterService.updateClubInfo(clubInfoRegisterDto);

            // メッセージを取得
            String resultMessage = messageSource.getMessage(result, null, Locale.getDefault());

            // アップデートできたら部署情報登録画面に遷移
            if ("updateClubInfo".equals(result)) {
                mav.addObject("updateClubInfo", resultMessage);
                mav.addObject("leaderClubId", leaderClubId);
                mav.setViewName("ClubInfoRegister");
            } else {
                mav.setViewName("error");
            }

            // DB接続失敗した場合、エラー画面に遷移
        } catch (Exception e) {
            mav.setViewName("error");
        }
        return mav;
    }

}