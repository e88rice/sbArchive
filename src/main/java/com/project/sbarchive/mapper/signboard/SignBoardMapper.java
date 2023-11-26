package com.project.sbarchive.mapper.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SignBoardMapper {
    
    // 등록
    void addSignboard(SignBoardVO signBoardVO);

    SignBoardVO getSignboard(int signboardId);

    // 간판 리스트 (메인-지도용)
    ArrayList<SignBoardAllDTO> getSignboardList();

    // 간판 총 갯수 구하기
    int getCount();

    // 페이징된 간판 리스트 (간판-리스트용)
    ArrayList<SignBoardAllDTO> getSignboardListWithPaging(PageRequestDTO pageRequestDTO);

    // 간판 게시글 콘텐츠, 수정날짜 수정
    void modifySignboard(int signboardId, String content);

    // 간판 게시글 삭제
    int removeSignboard(int signboardId);

    // 메인페이지에서 검색 기능 이용 시 키워드에 해당하는 객체를 반환받음
    ArrayList<SignBoardAllDTO> getSearchSignboardList(String keyword);
}
