package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("top")
public class TopController {
    
    @GetMapping
    public String top(Model model) {
        return "top";
    }
    
}
