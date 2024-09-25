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
        mav.addObject("loginForm", new LoginForm());
        mav.setViewName("login");
        return mav;
    }

    @PostMapping
    ModelAndView checkLoginData(@Valid LoginForm paramForm, BindingResult bindingResult, HttpSession session) {

        ModelAndView mav = new ModelAndView();

        // dtoに値をセット
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(paramForm.getUserId());
        loginDto.setClubId(paramForm.getClubId());
        loginDto.setLoginName(paramForm.getLoginName());
        loginDto.setPassword(paramForm.getPassword());

        // バリデーション
        if (bindingResult.hasErrors()) {
            mav.setViewName("login");
            return mav;
        }

        LoginDto loginData = loginService.getLoginData(loginDto);

        // formに値をセット
        LoginForm responseForm = new LoginForm();
        responseForm.setUserId(loginData.getUserId());
        responseForm.setClubId(loginData.getClubId());
        responseForm.setLoginName(loginData.getLoginName());
        responseForm.setPassword(loginData.getPassword());

        if (!ObjectUtils.isEmpty(responseForm.getLoginName())) {
            // sessionに値をセット
            session.setAttribute("loginName", responseForm.getLoginName());
            session.setAttribute("userId", responseForm.getUserId());
            session.setAttribute("clubId", responseForm.getClubId());
            // トップ画面に遷移
            mav.addObject("leaderClubId", responseForm.getClubId());
            mav.setViewName("redirect:/top");

        } else {
            // ログイン情報に間違いがある場合、ログイン画面にリダイレクト
            mav.addObject("error", "ログイン情報が間違っています。<br>正しいログイン名とパスワードを入力してください。");
            mav.setViewName("login");

        }

        return mav;
    }

}
