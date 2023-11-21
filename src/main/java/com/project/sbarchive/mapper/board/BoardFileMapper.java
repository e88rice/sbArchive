package com.project.sbarchive.mapper.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface BoardFileMapper {

    void addBoardImages(int boardId, String fileName, String type);

    ArrayList<String> getBoardImages(int boardId, String type);

    // 해당 signboardId를 참조하는 이미지 데이터들을 삭제
    void removeBoardImages(int boardId, String type);

}
