package jp.co.jc21ps.activity_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.dto.ActivityDto;
import jp.co.jc21ps.activity_management.repository.ActivityRepository;

@Service
public class TopService {
    
    private final ActivityRepository activityRepository;

    public TopService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll();
    }
}
