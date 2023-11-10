package com.project.sbarchive.controller.main;

import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class MainController {

    @RequestMapping("/index")
    public String index() {
        log.info("HI");

        return "/main/main";
    }


}
