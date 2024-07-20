package jp.co.jc21ps.activity_management.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.jc21ps.activity_management.dto.ActivityDto;
import jp.co.jc21ps.activity_management.service.TopService;

@Controller
@RequestMapping("top")
public class TopController {

    private final TopService topService;

    public TopController(TopService topService) {
        this.topService = topService;
    }
    
    @GetMapping
    public String top(Model model) {
        List<ActivityDto> activities = topService.getAllActivities();
        model.addAttribute("activities", activities);
        return "top";
    }
    
}
