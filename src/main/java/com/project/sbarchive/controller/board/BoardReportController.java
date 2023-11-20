package com.project.sbarchive.controller.board;

import com.project.sbarchive.dto.board.BoardAllDTO;
import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.service.board.BoardFileService;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boardReport")
public class BoardReportController {
    private final BoardService boardService;

    private final BoardFileService boardFileService;

    private final UserService userService;

    private final ModelMapper modelMapper;


    @GetMapping("/add")
    public void addBoard(int boardId, Principal principal, Model model) {
        BoardDTO boardDTO = boardService.getBoard(boardId);
        BoardAllDTO boardAllDTO = modelMapper.map(boardDTO,BoardAllDTO.class);
        model.addAttribute("dto", boardAllDTO);
        model.addAttribute("user",principal);
    }
}
