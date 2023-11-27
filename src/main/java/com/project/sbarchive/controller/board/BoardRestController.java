package com.project.sbarchive.controller.board;

import com.project.sbarchive.dto.board.BoardAllDTO;
import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.board.BoardReportDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.service.board.BoardFileService;
import com.project.sbarchive.service.board.BoardReprotService;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.message.MessageService;
import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boardReport")
public class BoardRestController {

    private final BoardService boardService;

    private final ModelMapper modelMapper;

    @DeleteMapping("/alldel")
    public void deleteAlldelTime() {
        boardService.deleteAllDelDate();
    }

}
