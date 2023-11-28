package com.project.sbarchive.service.board;

import com.project.sbarchive.mapper.board.BoardFileMapper;
import com.project.sbarchive.service.staticResource.StaticResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {


    private final StaticResourceService staticResourceService;

    private final BoardFileMapper boardFileMapper;

    @Override
    public void addBoardImages(int boardId, List<MultipartFile> files,String type) {

        String uploadPath = staticResourceService.getStaticFolderPath() + "\\imgs\\";
        uploadPath = uploadPath.replace("build\\resources\\main\\", "src\\main\\resources\\");
        String uploadPath1 = uploadPath + "board\\";

        for(MultipartFile multipartFile : files) { // 전달된 파일의 수 만큼 순회
            String originalName = multipartFile.getOriginalFilename(); // 전달 된 파일의 파일명
            log.info("테스트 이름 : " + originalName);

            String uuid = UUID.randomUUID().toString(); // 16자리. 파일명이 겹치지 않게 임의의 값을 생성해주는 친구

            // uploadPath = c:\\upload  uuid = bf8f3461-c18d-4485-a526-519727e881a4  originalName = sprite__common.png
            Path savePath = Paths.get(uploadPath1, uuid + "_" + originalName);
            log.info("테스트 경로 : " + originalName);

            boolean isImage = false; // 전달 된 파일이 이미지 형식인지 판단

            try {
                multipartFile.transferTo(savePath); // 실제 파일 저장
                // 이미지 파일이라면
                if (Files.probeContentType(savePath) != null && Files.probeContentType(savePath).startsWith("image")) {
                    log.info(Files.probeContentType(savePath)); // image/jpeg
                    isImage = true;
                    File thumbFile = new File(uploadPath1, "s_" + uuid + "_" + originalName);
                    log.info("1" + savePath.toFile());
                    log.info("---------/////////-----------------------1");

                    // savePath.toFile() = 원본 파일의 경로. c:\\upload\\6dde0d36-c580-4fe4-865a-9dde6fbf7a0a_고양이.jpg
                    // thumbFile = 새로 생기는 파일의 경로 및 파일 이름. c:\\upload\\s_6dde0d36-c580-4fe4-865a-9dde6fbf7a0a_고양이.jpg
                    // width, height = 이미지 파일의 최대 크기
                    log.info("---------/////////-----------------------2");
                    boardFileMapper.addBoardImages(boardId, uuid+"_"+originalName ,type);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getBoardImages(int boardId, String type) {
        ArrayList<String> files = boardFileMapper.getBoardImages(boardId, type);
        return files;
    }

    @Override
    public void removeBoardImages(int boardId, String type) {
        ArrayList<String> files = boardFileMapper.getBoardImages(boardId, type);
        String uploadPath = staticResourceService.getStaticFolderPath() + "\\imgs\\";
        uploadPath = uploadPath.replace("build\\resources\\main\\", "src\\main\\resources\\");
        String deletePath = uploadPath + "board\\";
        for(String file : files) {
            File filePath = new File(deletePath + file);
            File thumbnail = new File(deletePath + "s_" + file);
            log.info(filePath.getPath());
            log.info(thumbnail.getPath());
            filePath.delete();
            thumbnail.delete();
        }
        boardFileMapper.removeBoardImages(boardId, type);
    }
}
