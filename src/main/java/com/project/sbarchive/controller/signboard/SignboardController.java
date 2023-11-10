package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/signboard")
@RequiredArgsConstructor
public class SignboardController {

    private final SignBoardService signBoardService;

    private final SignBoardFileService signBoardFileService;

    // 게시글 등록 페이지
    @GetMapping("/add") // 간판 등록 페이지로 이동
    public String addGET() {
        return "/signboard/add";
    }

    // 게시글 등록
    @PostMapping(value = "/add") // 간판 등록, 간판 등록 시 업로드한 이미지도 등록
    public String addPOST(SignBoardDTO signBoardDTO, List<MultipartFile> files) {

        System.out.println("signboard : registerPost ...");
        int signBoardId = signBoardService.add(signBoardDTO); // 등록 버튼을 누른 게시글을 DB에 저장하고 방금 등록한 게시물의 id값을 받아옴

        System.out.println(signBoardId);
        if(files.size() > 0) {
            signBoardFileService.add(signBoardId, files); // 받아온 id값에 해당하는 보드의 파일들도 DB에 저장
        }

        return "redirect:/index";
    }

    // 게시글 리스트 페이지
    @GetMapping("/list/{page}")
    public String listGET(@PathVariable("page") int pageNum, Model model) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(pageNum).size(12).build();
        PageResponseDTO<SignBoardAllDTO> responseDTO = signBoardService.getListWithPaging(pageRequestDTO);
        model.addAttribute("response", responseDTO);
        return "/signboard/list";
    }


}
