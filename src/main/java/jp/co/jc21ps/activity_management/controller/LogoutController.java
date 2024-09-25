package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.activity_management.form.LoginForm;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // ログアウトボタン押下時、ログイン画面に遷移する
        session.invalidate();
        mav.addObject("loginForm", new LoginForm());
        mav.setViewName("login");
        return mav;
    }

}
