package jp.co.jc21ps.activity_management.service;

import org.springframework.stereotype.Service;

import jp.co.jc21ps.activity_management.dto.LoginDto;
import jp.co.jc21ps.activity_management.entity.LoginEntity;
import jp.co.jc21ps.activity_management.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // ログイン処理
    public LoginDto getLoginData(LoginDto paramDto) {

        // entityに値をセット
        LoginEntity paramListEntity = new LoginEntity();
        paramListEntity.setUserId(null);
        paramListEntity.setClubId(null);
        paramListEntity.setLoginName(paramDto.getLoginName());
        paramListEntity.setPassword(paramDto.getPassword());

        LoginEntity loginData = loginRepository.getLoginData(paramListEntity);

        // dtoに値をセット
        LoginDto responseDto = new LoginDto();
        responseDto.setUserId(loginData.getUserId());
        responseDto.setClubId(loginData.getClubId());
        responseDto.setLoginName(loginData.getLoginName());
        responseDto.setPassword(null);

        return responseDto;

    }

}
