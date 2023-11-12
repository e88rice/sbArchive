package com.project.sbarchive.mapper.signboard;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface SignBoardFileMapper {

    // PK값을 가진 게시글에 이미지들 추가
    void addSignboardImages(int signboardId, String fileName);

    // PK값을 가진 게시글의 이미지들 가져옴
    ArrayList<String> getSignboardImages(int signboardId);

    // 해당 signboardId를 참조하는 이미지 데이터들을 삭제
    void removeSignboardImages(int signboardId);

}
