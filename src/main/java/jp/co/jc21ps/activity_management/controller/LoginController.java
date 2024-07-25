package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.jc21ps.activity_management.dto.LoginDto;
import jp.co.jc21ps.activity_management.form.LoginForm;
import jp.co.jc21ps.activity_management.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public ModelAndView index(Model model) {
        ModelAndView mav = new ModelAndView();
      mav.setViewName("/login.html");
        return mav;
    }

    @PostMapping
    ModelAndView postResult(LoginForm loginForm,HttpSession session){
      ModelAndView mav = new ModelAndView();
      mav.setViewName("/login.html");
      LoginDto loginDto = new LoginDto(null, null, loginForm.getLoginName(), loginForm.getPassword());
      LoginDto loginImfoReturnDto = new LoginDto(null, null, null, null);
      // 入力値がnullまたは空文字列かチェック
      if (loginForm.getLoginName().isEmpty()||loginForm.getLoginName() == null || loginForm.getPassword().isEmpty() || loginForm.getPassword() == null) {
        mav.setViewName("/login.html");
        mav.addObject("error", "ログイン名またはパスワードを入力してください。");
        return mav;
        }

      //桁数チェック
      if (loginForm.getLoginName().length() >= 30 || loginForm.getPassword().length() >= 30) {
        mav.setViewName("/login.html");
        mav.addObject("error", "ログイン名またはパスワードが長すぎます。（最大30文字まで）");
        return mav;
      }

      //半角英数字チェック
      if (!loginForm.getLoginName().matches("^[a-zA-Z0-9]+$") || !loginForm.getPassword().matches("^[a-zA-Z0-9]+$") ) {
        mav.setViewName("/login.html");
        mav.addObject("error", "ログイン名またはパスワードは半角英数字で入力してください。");
        return mav;
      }
      loginImfoReturnDto = loginService.getLoginOne(loginDto);

        //セッションにdtoからとれたデータを詰める
      if(!ObjectUtils.isEmpty(loginImfoReturnDto.getLoginName())){ 
        System.out.println(loginImfoReturnDto.getLoginName());
      session.setAttribute("loginName",loginImfoReturnDto.getLoginName());
      session.setAttribute("userId",loginImfoReturnDto.getUserId());
      session.setAttribute("clubId",loginImfoReturnDto.getClubId());
        //トップに遷移
        //画面に埋め込みたいとき→addObject(html側の名前,formのメソッド名)
      mav.setViewName("redirect/top");
        // ログイン成功
      }else{
        mav.setViewName("/login.html");
        mav.addObject("error", "ログイン情報が間違っています。正しいログイン名とパスワードを入力してください。");
      
      } 

      return mav;
    }
       

    
    
    
}
