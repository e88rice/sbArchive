package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/signboard")
@RequiredArgsConstructor
public class SignboardController {

    private final SignBoardService signBoardService;

    private final SignBoardFileService signBoardFileService;

    @GetMapping("/add")
    public String registerGET() {
        log.info("ㅎㅇㅎㅇ");
        return "/signboard/add";
    }

    @PostMapping(value = "/add")
    public String registerPOST(SignBoardDTO signBoardDTO, List<MultipartFile> files) {

        int signBoardId = signBoardService.add(signBoardDTO);

        System.out.println(signBoardId);
        if(files.size() > 0) {
            signBoardFileService.add(signBoardId, files);
        }

        return "redirect:/index";
    }
}
