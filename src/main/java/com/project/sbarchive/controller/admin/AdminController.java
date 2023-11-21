package com.project.sbarchive.controller.admin;

import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/management")
    public void adminPage() {

    }

}

// 게시글, 댓글, 신고 활동 시 마다 이거 써줄거임 
//        userService.lvPointUp(userId);
//                userVO = userService.getUserInfo(userId);
//                userService.checkLevelUp(userId, userVO.getLevel(), userVO.getLvPoint());
//                log.info(userVO);
