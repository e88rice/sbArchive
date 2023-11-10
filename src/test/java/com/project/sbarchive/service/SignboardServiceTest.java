package com.project.sbarchive.service;

import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class SignboardServiceTest {

    @Autowired(required = false)
    private SignBoardService signBoardService;

    @Test
    public void getCount() {
        log.info(signBoardService.getCount());
    }

    @Test
    public void getList() {
        signBoardService.getList().forEach(signBoardAllDTO -> {
            log.info(signBoardAllDTO);
        });
    }

    @Test
    public void getListWithPaging() {
        signBoardService.getList().forEach(signBoardAllDTO -> {
            log.info(signBoardAllDTO);
        });
    }



}
