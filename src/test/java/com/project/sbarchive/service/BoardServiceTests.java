package com.project.sbarchive.service;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.service.board.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired(required = false)
    private BoardService boardService;
    @Test
    public void addBoard100() {
        for(int i = 0 ; i < 100 ; i ++) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .userId("papago")
                    .content("testContent..." + i)
                    .title("제목" + i)
                    .nickname("papago")
                    .build();
            boardService.add(boardDTO);
        }
    }
    @Test
    public void modifyBoard() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("update...")
                .content("modify....")
                .boardId(202)
                .build();
        boardService.modify(boardDTO);
    }
}
