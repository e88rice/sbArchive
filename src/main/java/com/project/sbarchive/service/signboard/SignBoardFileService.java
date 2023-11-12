package com.project.sbarchive.service.signboard;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface SignBoardFileService {

    // signboardId를 참조하는 게시글에 이미지들 추가
    void addSignboardImages(int signboardId, List<MultipartFile> files);

    // signboardId를 참조하는 게시글의 이미지들 가져옴
    ArrayList<String> getSignboardImages(int signboardId);

    // 해당 signboardId를 참조하는 이미지 데이터들을 삭제
    void removeSignboardImages(int signboardId);


}
