package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import jp.co.jc21ps.activity_management.dto.LoginDto;
import jp.co.jc21ps.activity_management.form.LoginForm;
import jp.co.jc21ps.activity_management.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public ModelAndView dispLogin(Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }

    @PostMapping
    ModelAndView checkLoginData(@Valid LoginForm paramForm, BindingResult bindingResult, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login");
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(paramForm.getUserId());
        loginDto.setClubId(paramForm.getClubId());
        loginDto.setLoginName(paramForm.getLoginName());
        loginDto.setPassword(paramForm.getPassword());

        // バリデーション機能を使ってLoginFormの＠がついている変数のチェックを行う
        if (bindingResult.hasErrors()) {
            mav.setViewName("/login");

            return mav;
        }
        LoginDto loginData = loginService.getLoginData(loginDto);
        LoginForm responseForm = new LoginForm();
        responseForm.setUserId(loginData.getUserId());
        responseForm.setClubId(loginData.getClubId());
        responseForm.setLoginName(loginData.getLoginName());
        responseForm.setPassword(loginData.getPassword());

        // セッションにdtoからとれたデータを詰める
        if (!ObjectUtils.isEmpty(responseForm.getLoginName())) {
            session.setAttribute("loginName", responseForm.getLoginName());
            session.setAttribute("userId", responseForm.getUserId());
            session.setAttribute("clubId", responseForm.getClubId());

            // トップに遷移
            // 画面に埋め込みたいとき→addObject(html側の名前,formのメソッド名)
            mav.addObject("leaderClubId", responseForm.getClubId());
            mav.setViewName("redirect:/top");
        } else {
            mav.addObject("error", "ログイン情報が間違っています。正しいログイン名とパスワードを入力してください。");
            mav.setViewName("/login");
        }

        return mav;
    }

}
