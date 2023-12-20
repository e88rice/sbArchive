package com.project.sbarchive.controller.map;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class MapController {
    @RequestMapping("/archive/map")
    public String showMap() {

        return "main/main";
    }

}
