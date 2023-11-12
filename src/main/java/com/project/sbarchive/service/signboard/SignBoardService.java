package com.project.sbarchive.service.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.vo.signboard.SignBoardVO;

import java.util.ArrayList;

public interface SignBoardService {

    int addSignboard(SignBoardDTO signBoardDTO); // 간판 게시물 추가

    SignBoardDTO getSignboard(int signboardId); // 간판 1개 불러오기

    ArrayList<SignBoardAllDTO> getSignboardList(); // 페이징 없이 모든 간판 게시물 리스트를 가져옴

    int getCount(); // 간판 게시물 객체의 총 갯수

    PageResponseDTO<SignBoardAllDTO> getSignboardListWithPaging(PageRequestDTO pageRequestDTO); // 페이징 된 간판 게시물 객체 리스트

    Integer modifySignboard(int signboardId, String content); // 간판 게시물 콘텐츠 수정

    int removeSignboard(int signboardId); // 해당 signboardId를 참조하는 이미지 데이터들을 삭제
}
