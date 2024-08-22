package jp.co.jc21ps.activity_management.service;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import jp.co.jc21ps.dto.SessionDto;


@Service
public  class CommonService {

    

    public SessionDto getCommonService(HttpSession session) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setUserId((String) session.getAttribute("userId"));
        sessionDto.setClubId((String) session.getAttribute("clubId"));
        sessionDto.setUserName((String) session.getAttribute("userName"));
        
        return sessionDto;
    }    
}
