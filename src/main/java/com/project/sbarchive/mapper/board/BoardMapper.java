package com.project.sbarchive.mapper.board;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {


    void add(BoardVO boardVO);

    List<BoardVO> getBoardList();

    BoardVO getBoard(int boardId);

    void modify(BoardVO boardVO);

    void hitCount(int boardId);

    void likeUp(int boardId, int likeUp);

    void remove(int boardId);

    List<BoardVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);


}
