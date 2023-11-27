package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.board.BoardReportDTO;
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
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final ModelMapper modelMapper;



    @Override
    public int add(BoardDTO boardDTO) {
        BoardVO boardVO = modelMapper.map(boardDTO, BoardVO.class);
        boardMapper.add(boardVO);
        return boardVO.getBoardId();
    }

    @Override
    public int addNotice(BoardDTO boardDTO) {
        BoardVO boardVO = modelMapper.map(boardDTO, BoardVO.class);
        boardMapper.addNotice(boardVO);
        return boardVO.getBoardId();
    }

    @Override
    public List<BoardDTO> getBoardList() {
        List<BoardVO> boardVOList = boardMapper.getBoardList();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardVOList.forEach(boardVO -> boardDTOList.add(modelMapper.map(boardVO,BoardDTO.class)));
        return boardDTOList;
    }

    @Override
    public BoardDTO getBoard(int boardId) {
        log.info(boardId + "id!!!!!!!!!!!!!!!!!!!!!!!!");
        BoardVO boardVO = boardMapper.getBoard(boardId);
        log.info("boardVO!!!!!!!!!!!!!" + boardVO);
        BoardDTO boardDTO = modelMapper.map(boardVO,BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        BoardVO boardVO = modelMapper.map(boardDTO,BoardVO.class);
        boardMapper.modify(boardVO);
    }

    @Override
    public void hitCount(int boardId) {
        boardMapper.hitCount(boardId);
    }

    @Override
    public void remove(int boardId) {
        boardMapper.remove(boardId);
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



    @Override
    public void likeUp(int boardId, String userId, String title) {
        boardMapper.likeUp(boardId,userId, title);

        log.info(boardId + "like!!");
    }

    @Override
    public void likeDown(int boardId, String userId) {
        boardMapper.likeDown(boardId,userId);
        log.info(boardId + userId + "unlike!!");
    }

    @Override
    public int getLike(int boardId, String userId) {
        return  boardMapper.getLike(boardId,userId);
    }

    @Override
    public void boardlikeUp(int boardId) {
        boardMapper.boardlikeUp(boardId);
    }

    @Override
    public void boardlikeDown(int boardId) {
        boardMapper.boardlikeDown(boardId);
    }


    // delDate 기준 만료된 보드들을 페이징해서 가져옴
    @Override
    public PageResponseDTO<BoardDTO> getExBoards(PageRequestDTO pageRequestDTO) {

        List<BoardVO> voList = boardMapper.getExBoards(pageRequestDTO);

        List<BoardDTO> dtoList = new ArrayList<>();
        for (BoardVO boardVO : voList) {
            dtoList.add(modelMapper.map(boardVO, BoardDTO.class));
        }

        int total = boardMapper.getExBoardsCount(pageRequestDTO);

        return PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public int getExBoardsCount(PageRequestDTO pageRequestDTO) {
        return boardMapper.getExBoardsCount(pageRequestDTO);
    }

    @Override
    public void deleteAllDelDate() {
        boardMapper.deleteAllDelDate();
    }
}
