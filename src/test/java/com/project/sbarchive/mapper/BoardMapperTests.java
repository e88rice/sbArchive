package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.mapper.board.BoardMapper;
import com.project.sbarchive.vo.board.BoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class BoardMapperTests {

    @Autowired(required = false)
    private BoardMapper boardMapper;

    @Test
    public void getExBoardsTest() {
        List<BoardVO> exBoards = boardMapper.getExBoards(PageRequestDTO.builder().page(1).size(10).build());
        exBoards.forEach(boardVO -> log.info(boardVO));
        log.info(boardMapper.getExBoardsCount(PageRequestDTO.builder().build()));
    }

    @Test
    public void delAll() {
        boardMapper.deleteAllDelDate();
    }

}
