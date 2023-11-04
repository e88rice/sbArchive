package com.project.sbarchive.controller;

import com.project.sbarchive.dto.SignBoardDTO;
import com.project.sbarchive.service.SignBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class MainController {

    @Autowired
    private SignBoardService signBoardService;


    @RequestMapping("/index")
    public String index() {
        log.info("HI");

        return "/main";
    }

    @GetMapping("/register")
    public String registerGET() {

        return "/register";
    }

    @PostMapping("/register")
    public String registerPOST(SignBoardDTO signBoardDTO) {
        log.info(signBoardDTO);
        signBoardService.register(signBoardDTO);

        return "redirect:/index";
    }

}
