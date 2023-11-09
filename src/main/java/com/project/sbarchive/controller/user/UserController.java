package com.project.sbarchive.controller.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
