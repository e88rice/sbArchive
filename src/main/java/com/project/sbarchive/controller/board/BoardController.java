package com.project.sbarchive.controller.board;

import com.project.sbarchive.dto.board.BoardAllDTO;
import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.service.board.BoardFileService;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;


@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    private final BoardFileService boardFileService;

    private final UserService userService;

    private final ModelMapper modelMapper;

//    private final BoardFileService boardFileService;


    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @GetMapping("/add")
    public void addBoard() {

    }
    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @PostMapping("/add")
    public String addBoard(BoardDTO boardDTO, List<MultipartFile> files,
                           RedirectAttributes redirectAttributes) {
        log.info("addBoard -------" +  boardDTO);
        int boardId = boardService.add(boardDTO);
        for(MultipartFile file : files) {
            log.info(file);
        }
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                log.info("File: " + file.getOriginalFilename());
            }
            boardFileService.addBoardImages(boardId, files);
        }
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(Model model, @Valid PageRequestDTO pageRequestDTO,
                     BindingResult bindingResult) {
        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO = boardService.getList(pageRequestDTO);
        model.addAttribute("responseDTO",boardDTOPageResponseDTO );

    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만
    @GetMapping("/read")
    public void view(Model model, int boardId, HttpServletRequest request,
                     List<MultipartFile> files, PageRequestDTO pageRequestDTO) {
        BoardDTO boardDTO = boardService.getBoard(boardId);
        BoardAllDTO boardAllDTO = modelMapper.map(boardDTO, BoardAllDTO.class);
        boardAllDTO.setFiles(boardFileService.getBoardImages(boardId));
        model.addAttribute("dto", boardAllDTO);
        log.info("CONTROLLER VIEW!!" + boardDTO);
        boardService.hitCount(boardId);



    }
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만
    @GetMapping("/modify")
    public void modify(Model model, int boardId, HttpServletRequest request,
                         List<MultipartFile> files, PageRequestDTO pageRequestDTO) {
        BoardDTO boardDTO = boardService.getBoard(boardId);
        BoardAllDTO boardAllDTO = modelMapper.map(boardDTO, BoardAllDTO.class);
        boardAllDTO.setFiles(boardFileService.getBoardImages(boardId));
        model.addAttribute("dto", boardAllDTO);

    }

    @PreAuthorize("principal.username == #boardDTO.userId") // 로그인 정보와 전달받은 boardDTO의 네임이 같다면 작업 허용
    @PostMapping("/modify")
    public String modify(@Valid BoardDTO boardDTO,
                         List<MultipartFile> files,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info(boardDTO+"CONTROLLER MODIFY!!");
        boardService.modify(boardDTO);
        int boardId = boardDTO.getBoardId();
        boardFileService.removeBoardImages(boardId);

        log.info("removeIMG!!!!!!!!!!!!");
        if(files.size() == 0) {
            return "redirect:/board/read?boardId="+boardId;
        }
        boardFileService.addBoardImages(boardId, files); // 받아온 id값에 해당하는 보드의 파일들도 DB에 저장
        return "redirect:/board/read?boardId="+boardId;

    }

    @PreAuthorize("principal.username == #boardDTO.userId") // 로그인 정보와 전달받은 boardDTO의 네임이 같다면 작업 허용
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info(boardDTO.getBoardId() + "번 삭제!!!!!!!!!!!!!!");
        boardService.remove(boardDTO.getBoardId());
        redirectAttributes.addAttribute("page",1);
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/board/list?"+pageRequestDTO.getLink();
    }

    @ApiOperation(value = "remove 파일",notes = "DELETE 방식으로 삭제")
    @DeleteMapping("/board/remove/{boardId}")
    public void removeFile(@PathVariable int boardId) {
    boardFileService.removeBoardImages(boardId);
    }

}
