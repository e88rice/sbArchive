package com.project.sbarchive.controller.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public String registerUser(UserDTO userDTO) {
        log.info("===== registerUser Post Controller =====");
        userService.registerUser(userDTO);

        return "redirect:/login";
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
    public String loginGet() {
        log.info("===== loginGet Controller =====");
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginPost(String userId, String passwd, HttpSession session) {
        log.info("===== loginPost Controller =====");
        int isJoined = userService.loginCheck(userId, passwd);

        UserDTO loginInfo = new UserDTO();

        if(isJoined == 0) {
            session.setAttribute("msg", "로그인 실패");
            return "redirect:/user/login";
        } else {
            loginInfo = userService.getUserInfo(userId);
            session.setAttribute("loginInfo", loginInfo);
            session.removeAttribute("msg");

            log.info(loginInfo);

            }

        return "redirect:/index";
    }


}
