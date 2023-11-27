package com.project.sbarchive.mapper.board;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.vo.board.BoardReportVO;
import com.project.sbarchive.vo.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BoardReportMapper {
    void add(BoardReportVO boardReportVO);

    BoardReportVO getBoard(int rBoardId);

    int isAnswered(int rBoardId);


    void remove(int rBoardId);
    List<BoardReportVO> getBoardList();

    List<BoardReportVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);

    List<BoardReportVO> getMyReportList (PageRequestDTO pageRequestDTO, String userId);

    int getMyReportCount(String[] types, String keyword ,String userId);

}
