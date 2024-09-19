package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Logout")
public class LogoutController {

    @GetMapping
    public String logout(HttpSession session) {

        // ログアウトボタン押下時、ログイン画面に遷移する
        session.invalidate();
        return "redirect:/login";
    }

}
