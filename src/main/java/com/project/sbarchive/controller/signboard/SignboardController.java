package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/signboard")
@RequiredArgsConstructor
public class SignboardController {

    private final SignBoardService signBoardService;

    private final SignBoardFileService signBoardFileService;

    private final ModelMapper modelMapper;

    private final UserService userService;

    // 게시글 등록 페이지
    @GetMapping("/add") // 간판 등록 페이지로 이동
    public String addGET() {
        return "/signboard/add";
    }

    // 게시글 등록
    @PostMapping(value = "/add") // 간판 등록, 간판 등록 시 업로드한 이미지도 등록
    public String addPOST(SignBoardDTO signBoardDTO, List<MultipartFile> files, Principal principal) {

        System.out.println("signboard : addPost ...");

        UserVO userVO = userService.getUserInfo(principal.getName());

        signBoardDTO.setUserId(userVO.getUserId());
        signBoardDTO.setNickname(userVO.getNickname());
        int signBoardId = signBoardService.addSignboard(signBoardDTO); // 등록 버튼을 누른 게시글을 DB에 저장하고 방금 등록한 게시물의 id값을 받아옴

        log.info(signBoardDTO.getContent());

        for(MultipartFile file : files) {
            log.info(file);
        }

        System.out.println(signBoardId);
        if(files.size() > 0) {
            signBoardFileService.addSignboardImages(signBoardId, files); // 받아온 id값에 해당하는 보드의 파일들도 DB에 저장
        }

        return "redirect:/index";
    }

    // 게시글 리스트 페이지
    @GetMapping("/list/{page}")
    public String listGET(@PathVariable("page") int pageNum, Model model, Principal principal) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(pageNum).size(9).build(); // 사이즈 9 아니면 12로 설정할거임
        PageResponseDTO<SignBoardAllDTO> responseDTO = signBoardService.getSignboardListWithPaging(pageRequestDTO);
        model.addAttribute("response", responseDTO);
        model.addAttribute("username", principal.getName());
        return "/signboard/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/read/{page}/{signboardId}")
    public String readSignboard(@PathVariable("page") int pageNum, @PathVariable("signboardId") int signboardId, Model model) {
        SignBoardDTO signBoardDTO = signBoardService.getSignboard(signboardId);
        ArrayList<String> fileNames = signBoardFileService.getSignboardImages(signboardId);
        SignBoardAllDTO signBoardAllDTO = modelMapper.map(signBoardDTO, SignBoardAllDTO.class);
        signBoardAllDTO.setFiles(fileNames);
        model.addAttribute("dto", signBoardAllDTO);
        model.addAttribute("page", pageNum);

        return "/signboard/read";
    }


}
