package com.project.sbarchive.controller.main;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final SignBoardService signBoardService;
    private final BoardService boardService;

    @RequestMapping({"/index", "/main", "/"})
    public String index(Authentication authentication, Model model) {
        if(authentication != null) {
            log.info(authentication.getPrincipal());
        }

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(8).build();
        PageResponseDTO<SignBoardAllDTO> responseDTO = signBoardService.getSignboardListWithPaging(pageRequestDTO);
        model.addAttribute("signBoardResponse", responseDTO);
        log.info("@@@@@@@@@@@@@@@@@@@@ signBoardResponse @@@@@@@@@@@@@@@@@@@@@@@");
        log.info(responseDTO);

        ArrayList<BoardDTO> responseDTO2 = boardService.getIndexList();
        model.addAttribute("responseDTO2", responseDTO2);
        log.info("@@@@@@@@@@@@@@@@@@@@ responseDTO2 @@@@@@@@@@@@@@@@@@@@@@@");
        log.info(responseDTO2);


        return "index/index";
    }

    @GetMapping("/error/dupl")
    public void duplErrorPage() {

    }

    @RequestMapping({"/intro/introduction"})
    public String intro(Authentication authentication) {
        if(authentication != null) {
            log.info(authentication.getPrincipal());
        }
        return "intro/introduction";
    }


}
