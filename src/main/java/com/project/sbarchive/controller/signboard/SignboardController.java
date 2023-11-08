package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/signboard")
@RequiredArgsConstructor
public class SignboardController {

    private final SignBoardService signBoardService;

    @GetMapping("/add")
    public String registerGET() {
        log.info("ㅎㅇㅎㅇ");
        return "/signboard/add";
    }

    @PostMapping("/add")
    public String registerPOST(SignBoardDTO signBoardDTO) {
        log.info(signBoardDTO);
        signBoardService.register(signBoardDTO);

        return "redirect:/index";
    }
}
