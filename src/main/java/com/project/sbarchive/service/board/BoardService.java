package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;

import java.util.List;

public interface BoardService {
    int add(BoardDTO boardDTO);
    List<BoardDTO> getBoardList();

    BoardDTO getBoard(int boardId);

    void modify(BoardDTO boardDTO);

    void hitCount(int boardId);

    void remove(int boardId);

    //페이징
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);
}
