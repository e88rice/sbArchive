package com.project.sbarchive.mapper.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SignBoardMapper {
    
    // 등록
    void add(SignBoardVO signBoardVO);
    
    // 간판 리스트 (메인-지도용)
    ArrayList<SignBoardAllDTO> getList();

    // 간판 총 갯수 구하기
    int getCount();

    // 페이징된 간판 리스트 (간판-리스트용)
    ArrayList<SignBoardAllDTO> getListWithPaging(PageRequestDTO pageRequestDTO);
}
