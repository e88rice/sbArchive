package com.project.sbarchive.controller.my;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public void myPageGET(Principal principal, Model model) {
        log.info("===== myPage Get Controller =====");
        log.info("==================== myPage Get Controller ====================");
        UserVO userVO = userService.getUserInfo(principal.getName());
        UserDTO userDTO = modelMapper.map(userVO, UserDTO.class);

        model.addAttribute("userInfo", userDTO);
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

    @PostMapping("/modifyNickname")
    public String modifyNickname(String userId, String email, String nickname) {
        log.info("==================== modifyNickname POST Controller ====================");
        userService.modifyNickname(userId, email, nickname);

        return "redirect:/my/mypage";
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

    @GetMapping("/mySignBoardList")
    public void mySignBoardList() {
        log.info("==================== mySignBoardList Get Controller ====================");
    }

    @GetMapping("/myBoardList")
    public void myBoardList() {
        log.info("==================== myBoardList Get Controller ====================");
    }
}
