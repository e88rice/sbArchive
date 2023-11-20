package com.project.sbarchive.controller.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Log4j2
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String registerGET() {
        log.info("=============== registerUser Get Controller ===============");
        return "/user/join";
    }

    @PostMapping("/join")
    public String registerUser(UserDTO userDTO, RedirectAttributes redirectAttributes) {
        log.info("=============== registerUser Post Controller ===============");
        userService.registerUser(userDTO);

        return "redirect:/user/login";
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(String email) {
        log.info("=============== emailCheck Post Controller ===============");

        return userService.emailCheck(email);
    }

    @PostMapping("/userIdCheck")
    @ResponseBody
    public int userIdCheck(String userId) {
        log.info("=============== userIdCheck Post Controller ===============");

        return userService.userIdCheck(userId);
    }

    @GetMapping("/login")
    public String loginGet(String error, String logout, Principal principal) {
        log.info("=============== loginGet Controller ===============");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("user logout...");
        }

        if(principal != null){ // 로그인 상태면 인덱스로 돌아감. 로그인페이지로 접근금지.
            return "redirect:/index";
        }
        return "/user/login";
    }

    @GetMapping("/findId")
    public String findId(){
        log.info("=============== findId GET Controller ===============");
        return "/user/findId";
    }

    @PostMapping("/foundId")
    public String findId(String email, Model model){
        log.info("=============== findId POST Controller ===============");

        model.addAttribute("userId", userService.getUserId(email));
        log.info(userService.getUserId(email));
        return "/user/foundId";

    }

    @GetMapping("/foundId")
    public String foundId() {
        log.info("=============== foundId Get Controller ===============");
        return "/user/foundId";
    }

    @GetMapping("/findPassword")
    public String findPasswordGet() {
        log.info("=============== findPassword Get Controller ===============");
        return "/user/findPassword";
    }

    @PostMapping("/accountCheck")
    @ResponseBody
    public int accountCheck(String userId, String email) {
        log.info("=============== accountCheck Get Controller ===============");
        return userService.accountCheck(userId, email);
    }


}
