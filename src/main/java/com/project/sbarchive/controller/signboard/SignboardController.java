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

    // 게시글 등록
    @PostMapping(value = "/add")
    public String registerPOST(SignBoardDTO signBoardDTO, List<MultipartFile> files) {

        int signBoardId = signBoardService.add(signBoardDTO); // 등록 버튼을 누른 게시글을 DB에 저장하고 방금 등록한 게시물의 id값을 받아옴

        System.out.println(signBoardId);
        if(files.size() > 0) {
            signBoardFileService.add(signBoardId, files); // 받아온 id값에 해당하는 보드의 파일들도 DB에 저장
        }

        return "redirect:/index";
    }
}
