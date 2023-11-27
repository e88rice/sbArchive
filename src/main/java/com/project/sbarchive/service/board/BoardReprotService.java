package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardReportDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.vo.board.BoardReportVO;
import com.project.sbarchive.vo.board.BoardVO;

import java.util.List;

public interface BoardReprotService {
    int add(BoardReportDTO boardReportDTO);

    List<BoardReportDTO> getBoardList();

    BoardReportDTO getBoard(int rBoardId);

    int isAnswered(int rBoardId);


    void remove(int rBoardId);

    PageResponseDTO<BoardReportDTO> getList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardReportDTO> getMyReportList(PageRequestDTO pageRequestDTO, String keyword);


}
