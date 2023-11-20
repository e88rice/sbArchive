package com.project.sbarchive.mapper.board;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.board.BoardReportVO;
import com.project.sbarchive.vo.board.BoardVO;

import java.util.List;

public interface BoardReportMapper {
    void add(BoardReportVO boardReportVO);

    List<BoardVO> getBoardList();

    BoardReportVO getBoard(int boardId);

    void modify(BoardReportVO boardReportVO);

    void hitCount(int boardId);

    void remove(int boardId);

    List<BoardReportVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
}
