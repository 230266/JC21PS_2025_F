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
    public LoginDto getLoginService(LoginDto loginDtoParam) {
        LoginEntity loginEntity = new LoginEntity(loginDtoParam.getLoginName(), loginDtoParam.getPassword(), null, null);
        LoginEntity loginData = LoginRepository.getLoginData(loginEntity);
    
        //取ってきた値をdtoを介してcontrollerに投げる用 
        LoginDto loginDtoResp = new LoginDto(loginData.getUserId(),loginData.getClubId(), loginData.getLoginName(), loginData.getPassword());
        return loginDtoResp;
        //
    }
}
