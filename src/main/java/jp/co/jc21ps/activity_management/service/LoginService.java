package jp.co.jc21ps.activity_management.service;

import org.springframework.stereotype.Service;
import jp.co.jc21ps.activity_management.entity.User;
import jp.co.jc21ps.activity_management.repository.UserRepository;

@Service
public class LoginService {
    
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserOne() {
        User user = userRepository.getFirst();
        return user;
    }
}
