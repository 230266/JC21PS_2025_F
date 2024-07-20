package jp.co.jc21ps.activity_management.service;

import java.util.List;
import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.Activity;
import jp.co.jc21ps.activity_management.repository.ActivityRepository;

@Service
public class ActivityService {
    
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
