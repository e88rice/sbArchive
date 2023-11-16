package com.project.sbarchive.controller.my;

import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Log4j2
@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final UserService userService;

    @GetMapping("/mypage")
    public String myPageGET() {
        log.info("===== myPage Get Controller =====");
        log.info("==================== myPage Get Controller ====================");
        return "/my/mypage";
    }

    @GetMapping("/auth")
    public void authGET(Principal principal, Model model) {
        log.info("==================== authForm Get Controller ====================");
        String username = null;
        if(principal != null) { // 로그인 상태라면
            username = principal.getName();
        };
        model.addAttribute("username", username);
    }

    @PostMapping("/auth")
    public String authPOST(String userId, String passwd, Model model) {
        log.info("==================== authForm POST Controller ====================");
        boolean isConfirmPassword = userService.isConfirmPassword(userId, passwd);

        log.info("isConfirmPassword : " + isConfirmPassword);

        if(isConfirmPassword) {
            return "redirect:/my/modifyMyInfo";
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
            return "/my/auth";
        }
    }

    @GetMapping("/modifyMyInfo")
    public void modifyMyInfoGET() {
        log.info("==================== modifyMyInfo Get Controller ====================");
    }

    @GetMapping("/modifyPasswd")
    public void modifyPasswdGET() {
        log.info("==================== modifyPasswd Get Controller ====================");
    }

    @PostMapping("/modifyPasswd")
    public String modifyPasswdPOST(Principal principal, String passwd) {
        log.info("==================== modifyPasswd POST Controller ====================");
        userService.updatePassword(principal.getName(), passwd);

        return "redirect:/index";
    }

    @GetMapping("/existPasswdChecked")
    @ResponseBody
    public boolean existPasswdChecked(Principal principal, String passwd){
        log.info("==================== existPasswdChecked GET Controller ====================");
        return userService.isConfirmPassword(principal.getName(), passwd);
    }
}
