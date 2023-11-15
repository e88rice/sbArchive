package com.project.sbarchive.controller.my;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {

    @GetMapping("/mypage")
    public String myPageGET() {
        log.info("===== myPage Get Controller =====");
        return "/my/mypage";
    }
}
