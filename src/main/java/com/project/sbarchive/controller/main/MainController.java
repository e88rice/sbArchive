package com.project.sbarchive.controller.main;

import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class MainController {

    @RequestMapping({"/index", "/main", "/"})
    public String index(Authentication authentication) {
        log.info("HI");
        if(authentication != null) {
            log.info(authentication.getPrincipal());
        }
        return "/main/main";
    }


}
