package jp.co.jc21ps.activity_management.service;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.dto.LoginDto;
import jp.co.jc21ps.activity_management.entity.LoginEntity;
import jp.co.jc21ps.activity_management.repository.LoginRepository;

@Service
public class LoginService {
    
    private final LoginRepository LoginRepository;

    public LoginService(LoginRepository LoginRepository) {
        this.LoginRepository = LoginRepository;
    }

    //入力値を受け取ってRepositoryと接続する用
    public LoginDto getLoginOne(LoginDto loginDto) {
        LoginEntity loginEntity = new LoginEntity(loginDto.getLoginName(), loginDto.getPassword(), null, null);
        LoginEntity logins = LoginRepository.getLogin(loginEntity);
       // if(loginDto.getLoginName() == null || loginDto.getPassword() == null){
            //
        //}
    
    //取ってきた値をdtoを介してcontrollerに投げる用 
    LoginDto loginDto2 = new LoginDto(logins.getUserId(),logins.getClubId(), logins.getLoginName(), logins.getPassword());
        return loginDto2;
    }
}
