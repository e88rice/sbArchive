package com.project.sbarchive.service.signboard;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SignBoardFileService {

    void add(int signboardId, List<MultipartFile> files);
}
