package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.jc21ps.activity_management.form.LoginForm;
import jp.co.jc21ps.activity_management.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Logout")
public class LogoutController {
    private final CommonService commonService;

    public LogoutController(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
