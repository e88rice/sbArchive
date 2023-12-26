package com.project.sbarchive.service;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@SpringBootTest
@Log4j2
public class SignboardServiceTest {

    @Autowired(required = false)
    private SignBoardService signBoardService;

    @Autowired(required = false)
    private SignBoardMapper signBoardMapper;

    @Autowired(required = false)
    private SignBoardFileService fileService;


    @Test
    public void addDummy() {
        for(int i=0; i<= 50; i++){
            int id = signBoardService.addSignboard(SignBoardDTO.builder()
                    .userId("papago")
                    .nickname("papago")
                    .xOffSet("126.9843643")
                    .yOffSet("37.5704300")
                    .title("코리아 IT 아카데미 대구지점"+i)
                    .address("대구광역시 중구 중앙대로 366")
                    .content("테스트.."+i).build());
        }
    }

    @Test
    public void getCount() {
        log.info(signBoardService.getCount());
    }

    @Test
    public void getSignboardList() {
        signBoardService.getSignboardList().forEach(signBoardAllDTO -> {
            log.info(signBoardAllDTO);
        });
    }

    @Test
    public void getSignboard() {
        log.info(signBoardService.getSignboard(357));
    }

    @Test
    public void modifySignboard() {
        int result = signBoardService.modifySignboard(147,
                "변경된 콘텐츠...변경된 콘텐츠...변경된 콘텐츠...변경된 콘텐츠...변경된 콘텐츠...변경된 콘텐츠...");
        log.info(result);
    }

    @Test
    public void getSignboardListWithPaging() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).size(10).build();
        PageResponseDTO<SignBoardAllDTO> responseDTO = signBoardService.getSignboardListWithPaging(pageRequestDTO);
        responseDTO.getDtoList().forEach(signBoardAllDTO -> log.info(signBoardAllDTO));
        log.info("getTotal : " + responseDTO.getTotal());
        log.info("getPage : " + responseDTO.getPage());
        log.info("getSize : " + responseDTO.getSize());
        log.info("getSno : " + responseDTO.getSno());
        log.info("getStart : " + responseDTO.getStart());
        log.info("getEnd : " + responseDTO.getEnd());
    }

    @Test
    public void getSignboardImages() {

        ArrayList<String> files = fileService.getSignboardImages(146);
        for(String file : files) {
            log.info(file);
        }
    }

    @Test
    public void removeSignboardImages() {
        fileService.removeSignboardImages(147);
    }

    @Test
    public void getSBList() {
        signBoardService.getSearchSBList("이").forEach(
                signBoardAllDTO -> log.info(signBoardAllDTO)
        );
    }

}
