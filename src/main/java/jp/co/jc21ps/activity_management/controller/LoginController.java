package jp.co.jc21ps.activity_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.jc21ps.activity_management.dto.LoginDto;
import jp.co.jc21ps.activity_management.form.LoginForm;
import jp.co.jc21ps.activity_management.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


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
    ModelAndView postResult(@Valid LoginForm loginForm,BindingResult bindingResult,HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login.html");
        LoginDto loginDto = new LoginDto(null, null, loginForm.getLoginName(), loginForm.getPassword());
        LoginDto loginImfoReturnDto = new LoginDto(null, null, null, null);
        
        
        //バリデーション機能を使ってLoginFormの＠がついている変数のチェックを行う
        if (bindingResult.hasErrors()) {
          mav.setViewName("/login.html");
          return mav;
        }
        loginImfoReturnDto = loginService.getLoginOne(loginDto);

          //セッションにdtoからとれたデータを詰める
        if(!ObjectUtils.isEmpty(loginImfoReturnDto.getLoginName())){ 
            session.setAttribute("loginName",loginImfoReturnDto.getLoginName());
            session.setAttribute("userId",loginImfoReturnDto.getUserId());
            session.setAttribute("clubId",loginImfoReturnDto.getClubId());
          //トップに遷移
          //画面に埋め込みたいとき→addObject(html側の名前,formのメソッド名)
            mav.setViewName("redirect:/top");
          // ログイン成功
        }else{
            mav.setViewName("/login.html");
            mav.addObject("error", "ログイン情報が間違っています。正しいログイン名とパスワードを入力してください。");
          
        } 

    return mav;
    }
       

    
    
    
}
