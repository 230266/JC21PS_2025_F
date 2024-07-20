package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jp.co.jc21ps.activity_management.entity.User;
import jp.co.jc21ps.activity_management.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String index(Model model) {
        User user = loginService.getUserOne();
        model.addAttribute("name", user.getloginName());
        return "login";
    }

    @PostMapping
    public String postMethodName(Model model) {
        // ログイン成功
        return "redirect:top";
    }
    
    
}
