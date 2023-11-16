package com.project.sbarchive.service.board;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface BoardFileService {
    void addBoardImages(int boardId, List<MultipartFile> files);

    // signboardId를 참조하는 게시글의 이미지들 가져옴
    ArrayList<String> getBoardImages(int boardId);

    // 해당 signboardId를 참조하는 이미지 데이터들을 삭제
    void removeBoardImages(int boardId);
}
