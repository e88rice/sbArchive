package com.project.sbarchive.mapper.board;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insertBoard(BoardVO boardVO);

    List<BoardVO> selectAll();

    BoardVO read(int boardId);

    void update(BoardVO boardVO);

    void hit(int boardId, int hit);
    void like(int boardId, int like);

    void delete(int boardId);

    List<BoardVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
}
