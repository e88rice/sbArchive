package com.project.sbarchive.mapper.board;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.board.BoardReportVO;
import com.project.sbarchive.vo.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BoardReportMapper {
    void add(BoardReportVO boardReportVO);

    List<BoardReportVO> getBoardList();

    BoardReportVO getBoard(String userId);

    int isAnswered(int rBoardId);


    void remove(int rBoardId);

    List<BoardReportVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);

}
