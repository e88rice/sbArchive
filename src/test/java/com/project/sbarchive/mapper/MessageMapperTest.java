package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.mapper.message.MessageMapper;
import com.project.sbarchive.vo.message.MessageVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class MessageMapperTest {

    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Test
    public void add() {
        messageMapper.add("test", "test1", "ㅎㅇㅎㅇ");
    }

    @Test
    public void getAllMsgByReceiver() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(7).build();
        List<MessageVO> receiveMessages = messageMapper.getAllMsgByReceiver("test1", pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        for(MessageVO message : receiveMessages) {
            log.info(message);
        }
    }

    @Test
    public void getAllMsgByReceiverCount() {
        log.info("total received : " + messageMapper.getAllMsgByReceiverCount("test1"));
    }

    @Test
    public void getAllMsgBySender() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(7).build();
        List<MessageVO> sendMessages = messageMapper.getAllMsgBySender("test", pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        for(MessageVO message : sendMessages) {
            log.info(message);
        }
    }

    @Test
    public void getAllMsgBySenderCount() {
        log.info("total sent : " + messageMapper.getAllMsgBySenderCount("test1"));
    }

    @Test
    public void getMessage() {
        int index = 1;
        log.info(messageMapper.getMessage(index));
    }

    @Test
    public void checkMessage() {
        int index = 1;
        log.info(messageMapper.getMessage(index));
        messageMapper.checkMessage(index);
        log.info(messageMapper.getMessage(index));
    }

    @Test
    public void removeByReceiver() {
        int index = 1;
        messageMapper.removeByReceiver(index);
    }

    @Test
    public void removeBySender() {
        int index = 1;
        messageMapper.removeBySender(index);
    }

}
