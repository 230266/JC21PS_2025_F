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

    public LoginDto getLoginService(LoginDto loginDtoParam) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUserId(loginDtoParam.getUserId());
        loginEntity.setClubId(loginDtoParam.getClubId());
        loginEntity.setLoginName(loginDtoParam.getLoginName());
        loginEntity.setPassword(loginDtoParam.getPassword());

        LoginEntity loginData = LoginRepository.getLoginData(loginEntity);
        LoginDto loginDtoResp = new LoginDto();
        loginDtoResp.setUserId(loginData.getUserId());
        loginDtoResp.setUserId(loginData.getClubId());
        loginDtoResp.setUserId(loginData.getLoginName());
        loginDtoResp.setUserId(loginData.getPassword());

        return loginDtoResp;
    }
}
