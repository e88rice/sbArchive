package com.project.sbarchive.service.signboard;

import com.project.sbarchive.mapper.signboard.SignBoardFileMapper;
import com.project.sbarchive.service.staticResource.StaticResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
public class SignBoardFileServiceImpl implements SignBoardFileService{


    private final SignBoardFileMapper signBoardFileMapper;

    private final StaticResourceService staticResourceService;

    @Value("${com.project.sbarchive.upload.signboard.path}")
    private String uploadSignboardPath;

    @Override
    public void addSignboardImages(int signboardId, List<MultipartFile> files) {

        for(MultipartFile multipartFile : files) { // 전달된 파일의 수 만큼 순회
            String originalName = multipartFile.getOriginalFilename(); // 전달 된 파일의 파일명
            log.info("테스트 이름 : " + originalName);

            String uuid = UUID.randomUUID().toString(); // 16자리. 파일명이 겹치지 않게 임의의 값을 생성해주는 친구

            File serverFolder = new File(uploadSignboardPath);

            uploadSignboardPath = serverFolder.getAbsolutePath();

            // uploadPath = c:\\upload  uuid = bf8f3461-c18d-4485-a526-519727e881a4  originalName = sprite__common.png
            Path serverSavePath = Paths.get(uploadSignboardPath, uuid + "_" + originalName);
            log.info("@@@@@@@@@@@@@@@@@@@@@");
            log.info(serverSavePath);
            log.info("테스트 경로 : " + originalName);

            boolean isImage = false; // 전달 된 파일이 이미지 형식인지 판단
            try {
                if(!serverFolder.exists()) {
                    serverFolder.mkdirs();
                }
                multipartFile.transferTo(serverSavePath); // 실제 파일 저장
                // 이미지 파일이라면
                if ( Files.probeContentType(serverSavePath).startsWith("image")) {
                    isImage = true;
                    // savePath.toFile() = 원본 파일의 경로. c:\\upload\\6dde0d36-c580-4fe4-865a-9dde6fbf7a0a_고양이.jpg
                    // thumbFile = 새로 생기는 파일의 경로 및 파일 이름. c:\\upload\\s_6dde0d36-c580-4fe4-865a-9dde6fbf7a0a_고양이.jpg
                    // width, height = 이미지 파일의 최대 크기
                    signBoardFileMapper.addSignboardImages(signboardId, uuid+"_"+originalName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getSignboardImages(int signboardId) {
        ArrayList<String> files = signBoardFileMapper.getSignboardImages(signboardId);
        return files;
    }

    @Override
    public void removeSignboardImages(int signboardId) {
        ArrayList<String> files = signBoardFileMapper.getSignboardImages(signboardId);


        File serverFolder = new File(uploadSignboardPath);

        uploadSignboardPath = serverFolder.getAbsolutePath();

        log.info("src 경로 : " + uploadSignboardPath);
        for(String file : files) {
            File sFilePath = new File(uploadSignboardPath + file);
            log.info(sFilePath.getPath());
            sFilePath.delete();
        }
        signBoardFileMapper.removeSignboardImages(signboardId);
    }
}
