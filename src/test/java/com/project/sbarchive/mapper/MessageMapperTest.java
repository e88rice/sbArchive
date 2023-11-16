package com.project.sbarchive.mapper;

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
        messageMapper.add("test", "test2", "ㅎㅇㅎㅇ");
    }

    @Test
    public void getAllMsgByReceiver() {
        List<MessageVO> receiveMessages = messageMapper.getAllMsgByReceiver("test");
        for(MessageVO message : receiveMessages) {
            log.info(message);
        }
    }

    @Test
    public void getAllMsgBySender() {
        List<MessageVO> sendMessages = messageMapper.getAllMsgBySender("test1");
        for(MessageVO message : sendMessages) {
            log.info(message);
        }
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


}
