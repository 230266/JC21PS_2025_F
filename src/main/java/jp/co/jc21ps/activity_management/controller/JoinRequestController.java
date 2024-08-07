// package jp.co.jc21ps.activity_management.controller;

// import javax.naming.Binding;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;

// import jakarta.validation.Valid;
// import jp.co.jc21ps.activity_management.dto.JoinRequestDto;
// import jp.co.jc21ps.activity_management.form.JoinRequestForm;
// import jp.co.jc21ps.activity_management.service.JoinRequestService;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;



// @Controller
// @RequestMapping("/joinRequest")

// public class JoinRequestController {
    
//     @Autowired
//     private final JoinRequestService joinRequestService;

//     //サービスをセット
//     public JoinRequestControllery(JoinRequestService joinRequestService) {
//         this.joinRequestService joinRequestService;
//     }

//     @GetMapping("/dispJoinRequest")
//     public ModelAndView getJoinRequestByUserId(@PathVariable String userId, @Valid JoinRequestForm joinRequestForm, BindingResult bindingResult) {
        
//         //ModelAndViewのインスタンス化
//         ModelAndView mav = new ModelAndView();

//         //セッションからユーザーIDを取得
//         //String userId = (String) session.getAttribute("userId");

//         //userIdがセッションに存在しない場合、ログイン画面に遷移
//         //if(userId == null){
//           //mav.setViewName("Login");
//           //return mav;
//         //}

//         //formのインスタンス化
//         JoinRequestForm form = new JoinRequestForm();
        
//         //userIdのnullチェック
//         if (bindingResult.hasErrors()){
//             mav.setViewName("Error.html");
//             return mav;
//         }

//         //dtoのインスタンス化
//         JoinRequestDto joinDto = new JoinRequestDto();

//         //dtoにUserIdを詰め替える
//         joinDto.setUserId(form.getUserId());

//         //サービスのメソッドでデータを取得　
//         JoinRequestDto joinRequestDto = joinRequestService.findRequest(joinDto);

//         //formに渡すために部署名、部署説明をセットする
//         form.setClubName(joinRequestDto.getClubName());
//         form.setClubDescription(joinRequestDto.getClubDescription());

//         //Viewの名前を指定する
//         mav.setViewName("JoinRequest.html");

//         //formオブジェクトを追加
//         mav.addObject("form", form);
//         return mav;
//     }
    
// }
