package com.project.sbarchive.controller.board;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;


    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @GetMapping("/addBoard")
    public void addBoard() {
    }
    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @PostMapping("/addBoard")
    public String addBoard(BoardDTO boardDTO, HttpServletRequest httpServletRequest) {
        log.info("addBoard -------" +  boardDTO);
        boardService.add(boardDTO);
        return "redirect:/index";
    }

    @GetMapping("/boardList")
    public void list(Model model, @Valid PageRequestDTO pageRequestDTO,
                     BindingResult bindingResult) {
        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", boardService.getList(pageRequestDTO));

    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만
    @GetMapping({"/readBoard","/modifyBoard"})
    public void view(Model model,int boardId,HttpServletRequest request,PageRequestDTO pageRequestDTO) {

        String requestedUrl = request.getRequestURI();
        if(requestedUrl.equals("/board/readBoard")) {
            BoardDTO boardDTO = boardService.getBoard(boardId,"readBoard");
            boardService.hit(boardId,boardDTO.getHit());
            model.addAttribute("dto",boardDTO);
        } //특정보드아이디 게시물에 readBoard 라는코드가들어가야 조회수Hit 가 올라감

        BoardDTO boardDTO = boardService.getBoard(boardId,"modify");
        model.addAttribute("dto",boardDTO);
        log.info("CONTROLLER VIEW!!" + boardDTO);

    }

    @PreAuthorize("principal.username == #boardDTO.name") // 로그인 정보와 전달받은 boardDTO의 네임이 같다면 작업 허용
    @PostMapping("/modifyBoard")
    public String modify(@Valid BoardDTO boardDTO,
                         PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            log.info("CONTROLL ERROR!!");
            return "redirect:/board/modifyBoard";
        }
        log.info(boardDTO+"CONTROLLER MODIFY!!");
        boardService.modify(boardDTO);
        return "redirect:/board/boardList";
    }

    @PreAuthorize("principal.username == #boardDTO.name") // 로그인 정보와 전달받은 boardDTO의 네임이 같다면 작업 허용
    @PostMapping("/remove")
    public String remove(int boardId, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info(boardId+"번 삭제!!!!!!!!!!!!!!");
        boardService.remove(boardId);
        redirectAttributes.addAttribute("page",1);
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/board/boardList?"+pageRequestDTO.getLink();
    }

}
