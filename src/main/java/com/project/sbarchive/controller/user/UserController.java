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

@Log4j2
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String registerGET() {
        log.info("===== registerUser Get Controller =====");
        return "/user/join";
    }

    @PostMapping("/join")
    public String registerUser(UserDTO userDTO, RedirectAttributes redirectAttributes) {
        log.info("===== registerUser Post Controller =====");
        userService.registerUser(userDTO);

        return "redirect:/user/login";
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(String email) {
        log.info("===== emailCheck Post Controller =====");

        return userService.emailCheck(email);
    }

    @PostMapping("/userIdCheck")
    @ResponseBody
    public int userIdCheck(String userId) {
        log.info("===== userIdCheck Post Controller =====");

        return userService.userIdCheck(userId);
    }

    @GetMapping("/login")
    public void loginGet(String error, String logout) {
        log.info("===== loginGet Controller =====");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("user logout...");
        }
    }

//    @PostMapping("/login")
//    public String loginPost(String userId, String passwd, HttpSession session) {
//        log.info("===== loginPost Controller =====");
//        int isJoined = userService.loginCheck(userId, passwd);
//
//        UserDTO loginInfo = new UserDTO();
//
//        if(isJoined == 0) {
//            session.setAttribute("msg", "로그인 실패");
//            return "redirect:/user/login";
//        } else {
//            loginInfo = userService.getUserInfo(userId);
//            session.setAttribute("loginInfo", loginInfo);
//            session.removeAttribute("msg");
//
//            log.info(loginInfo);
//
//            }
//
//        return "redirect:/index";
//    }

    @GetMapping("/findId")
    public String findId(){
        log.info("===== findId GET Controller =====");
        return "/user/findId";
    }

    @PostMapping("/foundId")
    public String findId(String email, Model model){
        log.info("===== findId POST Controller =====");
        if(userService.emailCheck(email) == 0){
            model.addAttribute("msg", "이메일을 확인해주세요");
            return "/user/findId";
        } else {
            model.addAttribute("userId", userService.getUserId(email));
            log.info(userService.getUserId(email));
            return "/user/foundId";
        }
    }

    @GetMapping("/foundId")
    public String foundId() {
        log.info("===== foundId Get Controller =====");
        return "/user/foundId";
    }

}
