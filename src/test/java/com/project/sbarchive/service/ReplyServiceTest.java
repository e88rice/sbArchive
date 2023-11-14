package com.project.sbarchive.service;

import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.service.reply.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ReplyServiceTest {

    @Autowired(required = false)
    private ReplyService replyService;

    @Test
    public void addReply() {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .content("댓글 서비스 등록 테스트")
                .userId("admin")
                .nickname("admin")
                .boardId(2).build();
        replyService.addReply(replyDTO);
    }

    @Test
    public void getReply() {
        ReplyDTO replyDTO = replyService.getReply(7);
        log.info(replyDTO);
    }

    @Test
    public void testRemove() {
        replyService.removeReply(7);
    }

    @Test
    public void testModify() {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(7)
                .content("수정한 거임")
                .build();
        replyService.modifyReply(replyDTO);
    }

//    @Test
//    public void testGetList() {
//        List<ReplyDTO> dtoList=replyService.getReplyList(1);
//        dtoList.forEach(dto -> log.info(dto));
//    }

    @Test
    public void testGetCount() {
        log.info(replyService.getReplyCount(1));
    }



}
