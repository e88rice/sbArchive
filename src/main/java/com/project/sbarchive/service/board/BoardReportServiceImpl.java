package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardReportDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.mapper.board.BoardReportMapper;
import com.project.sbarchive.vo.board.BoardReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardReportServiceImpl implements BoardReprotService{

    private final BoardReportMapper boardReportMapper;
    private final ModelMapper modelMapper;



    @Override
    public int add(BoardReportDTO boardReportDTO) {
        BoardReportVO boardVO = modelMapper.map(boardReportDTO, BoardReportVO.class);
        boardReportMapper.add(boardVO);
        return boardVO.getRBoardId();
    }

    @Override
    public List<BoardReportDTO> getBoardList() {
        List<BoardReportVO> boardVOList = boardReportMapper.getBoardList();
        List<BoardReportDTO> boardDTOList = new ArrayList<>();
        boardVOList.forEach(boardVO -> boardDTOList.add(modelMapper.map(boardVO,BoardReportDTO.class)));
        return boardDTOList;
    }

    @Override
    public BoardReportDTO getBoard(String userId) {
        BoardReportVO boardVO = boardReportMapper.getBoard(userId);
        BoardReportDTO boardDTO = modelMapper.map(boardVO,BoardReportDTO.class);

        return boardDTO;
    }

    @Override
    public int isAnswered(int rBoardId) {
        return boardReportMapper.isAnswered(rBoardId);
    }

    @Override
    public void remove(int rBoardId) {
        boardReportMapper.remove(rBoardId);
    }

    @Override
    public PageResponseDTO<BoardReportDTO> getList(PageRequestDTO pageRequestDTO) {
        List<BoardReportVO> voList = boardReportMapper.selectList(pageRequestDTO);

        List<BoardReportDTO> dtoList = new ArrayList<>();
        for (BoardReportVO boardVO : voList) {
            dtoList.add(modelMapper.map(boardVO, BoardReportDTO.class));
        }

        int total = boardReportMapper.getCount(pageRequestDTO);

        PageResponseDTO<BoardReportDTO> pageResponseDTO = PageResponseDTO.<BoardReportDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

         return pageResponseDTO;
    }
}
