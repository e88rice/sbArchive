package com.project.sbarchive.service.board;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.vo.board.BoardVO;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {
    int add(BoardDTO boardDTO);
    int addNotice(BoardDTO boardDTO);
    List<BoardDTO> getBoardList();

    BoardDTO getBoard(int boardId);

    void modify(BoardDTO boardDTO);

    void hitCount(int boardId);

    void remove(int boardId);

    //페이징
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    void likeUp(int boardId, String userId, String title);

    void likeDown(int boardId, String userId);



    int getLike(int boardId, String userId);

    void boardlikeUp(int boardId);

    void boardlikeDown(int boardId);


    // delDate 기준 만료된 보드들을 페이징해서 가져옴
    PageResponseDTO<BoardDTO> getExBoards(PageRequestDTO pageRequestDTO);
    int getExBoardsCount(PageRequestDTO pageRequestDTO);

    void  deleteAllDelDate();

    int getAllBoardLike(int boardId);

    ArrayList<BoardDTO> getIndexList();

}
