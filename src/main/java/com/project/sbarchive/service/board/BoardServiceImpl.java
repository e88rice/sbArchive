package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.mapper.board.BoardMapper;
import com.project.sbarchive.vo.board.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements boardService{
    private final BoardMapper boardMapper;
    private final ModelMapper modelMapper;
    @Override
    public void add(BoardDTO boardDTO) {
        BoardVO boardVO = modelMapper.map(boardDTO, BoardVO.class);
        boardMapper.insertBoard(boardVO);
    }

    @Override
    public List<BoardDTO> getBoardList() {
        List<BoardVO> boardVOList = boardMapper.selectAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardVOList.forEach(boardVO -> boardDTOList.add(modelMapper.map(boardVO,BoardDTO.class)));
        return boardDTOList;
    }

    @Override
    public BoardDTO getBoard(int boardId, String hit) {
        BoardVO boardVO = boardMapper.read(boardId);
        BoardDTO boardDTO = modelMapper.map(boardVO,BoardDTO.class);
        int hit1 = boardDTO.getHit();
        if(hit.equals("view")) {
            boardMapper.hit(boardId,hit1);
        }

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        BoardVO boardVO = modelMapper.map(boardDTO,BoardVO.class);
        boardMapper.update(boardVO);
    }

    @Override
    public void hit(int boardId, int hit) {
        boardMapper.hit(boardId,hit);
    }

    @Override
    public void remove(int boardId) {
        boardMapper.delete(boardId);
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        List<BoardVO> voList = boardMapper.selectList(pageRequestDTO);

        List<BoardDTO> dtoList = new ArrayList<>();
        for (BoardVO boardVO : voList) {
            dtoList.add(modelMapper.map(boardVO, BoardDTO.class));
        }

        int total = boardMapper.getCount(pageRequestDTO);

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }
}
