package com.project.sbarchive.controller.admin;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/management")
    public void adminPage() {

    }

    @GetMapping("/exBoards/{page}")
    public String exBoards(Model model, PageRequestDTO pageRequestDTO, Principal principal,
                      @PathVariable("page") int page) {

        pageRequestDTO = PageRequestDTO.builder().page(page).size(20).build();

        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO = boardService.getExBoards(pageRequestDTO);
        model.addAttribute("responseDTO",boardDTOPageResponseDTO );

        if(principal != null) {
            String name = principal.getName();
            model.addAttribute("name" , name);
        }else {
            model.addAttribute("name", "guest");
        }

        log.info(boardDTOPageResponseDTO.getPage());
        log.info(boardDTOPageResponseDTO.getSize());
        log.info(boardDTOPageResponseDTO.getStart());
        log.info(boardDTOPageResponseDTO.getEnd());
        log.info(boardDTOPageResponseDTO.getDtoList().size());

        return "/admin/exBoardList";
    }
    @PostMapping("/targetDeleteAdmin")
    public String deleteSelectedBoard(int[] boardId) {
        log.info("====================admin POST DEL ====================");
        for(int id : boardId) {
            log.info(id);
            boardService.remove(id);
        }
        return "redirect:/admin/exBoards/1";
    }

}

// 게시글, 댓글, 신고 활동 시 마다 이거 써줄거임 
//        userService.lvPointUp(userId);
//                userVO = userService.getUserInfo(userId);
//                userService.checkLevelUp(userId, userVO.getLevel(), userVO.getLvPoint());
//                log.info(userVO);
