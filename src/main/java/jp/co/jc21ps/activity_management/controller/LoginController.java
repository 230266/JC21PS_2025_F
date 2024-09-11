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
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }

    @PostMapping
    ModelAndView postResult(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login.html");
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(loginForm.getUserId());
        loginDto.setClubId(loginForm.getClubId());
        loginDto.setLoginName(loginForm.getLoginName());
        loginDto.setPassword(loginForm.getPassword());

        LoginDto loginInfoReturnDto = new LoginDto();

        // バリデーション機能を使ってLoginFormの＠がついている変数のチェックを行う
        if (bindingResult.hasErrors()) {
            mav.setViewName("/login.html");

            return mav;
        }

        loginInfoReturnDto = loginService.getLoginService(loginDto);

        // セッションにdtoからとれたデータを詰める
        if (!ObjectUtils.isEmpty(loginInfoReturnDto.getLoginName())) {
            session.setAttribute("loginName", loginInfoReturnDto.getLoginName());
            session.setAttribute("userId", loginInfoReturnDto.getUserId());
            session.setAttribute("clubId", loginInfoReturnDto.getClubId());

            // トップに遷移
            // 画面に埋め込みたいとき→addObject(html側の名前,formのメソッド名)
            mav.addObject("leaderClubId", loginInfoReturnDto.getClubId());
            mav.setViewName("redirect:/top");
        } else {
            mav.addObject("login-error", "ログイン情報が間違っています。正しいログイン名とパスワードを入力してください。");
            mav.setViewName("/login.html");
        }

        return mav;
    }

}
