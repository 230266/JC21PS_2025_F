package jp.co.jc21ps.activity_management.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.jc21ps.activity_management.entity.Activity;
import jp.co.jc21ps.activity_management.service.ActivityService;

@Controller
@RequestMapping("top")
public class TopController {

    private final ActivityService activityService;

    public TopController(ActivityService activityService) {
        this.activityService = activityService;
    }
    
    @GetMapping
    public String top(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        return "top";
    }
    
}
