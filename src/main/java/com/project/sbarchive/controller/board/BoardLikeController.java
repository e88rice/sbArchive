package com.project.sbarchive.controller.board;

import com.project.sbarchive.service.board.BoardFileService;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boardLike")
public class BoardLikeController {
    private final BoardService boardService;




    @PostMapping("/remove/{boardId}/{userId}")
    public void removeLike(@PathVariable("boardId") int boardId, @PathVariable("userId") String userId) {
       boardService.likeUp(boardId,userId);
        boardService.boardlikeUp(boardId);
    }
    @PostMapping("/add/{boardId}/{userId}")
    public void addLike(@PathVariable("boardId") int boardId, @PathVariable("userId") String userId) {
        boardService.likeDown(boardId,userId);
        boardService.boardlikeDown(boardId);
    }
    @GetMapping("/get/{boardId}/{userId}")
    public void getLike(@PathVariable("boardId") int boardId, @PathVariable("userId") String userId) {
        boardService.getLike(boardId,userId);
    }

}
