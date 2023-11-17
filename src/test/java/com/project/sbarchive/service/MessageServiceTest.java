package com.project.sbarchive.service;

import com.project.sbarchive.dto.message.MessageDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.service.message.MessageService;
import com.project.sbarchive.vo.message.MessageVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class MessageServiceTest {

    @Autowired(required = false)
    private MessageService messageService;

    @Test
    public void add() {
        messageService.add("test", "test1", "ㅎㅇㅎㅇ");
    }

    @Test
    public void getAllMsgByReceiver() { // 받은 사람 기준 삭제 안한 쪽지들 전체 가져오기
        PageResponseDTO<MessageDTO> response = messageService.getAllMsgByReceiver("test1", PageRequestDTO.builder().page(1).size(7).build());
        for(MessageDTO message : response.getDtoList()) {
            log.info(message);
        }
    }

    @Test
    public void getAllMsgBySender() { // 보낸 사람 기준 삭제 안한 쪽지들 전체 가져오기
        PageResponseDTO<MessageDTO> response = messageService.getAllMsgBySender("test1", PageRequestDTO.builder().page(1).size(7).build());
        for(MessageDTO message : response.getDtoList()) {
            log.info(message);
        }
    }

    @Test
    public void getMessage() {
        int index = 1;
        log.info(messageService.getMessage(index)); // 객체 하나 가져오기
    }

    @Test
    public void checkMessage() {
        int index = 1;
        log.info(messageService.getMessage(index)); // 읽기 전
        messageService.checkMessage(index);
        log.info(messageService.getMessage(index)); // 읽은 후
    }

    @Test
    public void removeByReceiver() {
        int index = 1;
        messageService.removeByReceiver(index); // 받은 사람 기준 삭제
    }

    @Test
    public void removeBySender() {
        int index = 1;
        messageService.removeBySender(index); // 보낸 사람 기준 삭제
    }

}
