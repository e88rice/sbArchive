package com.project.sbarchive.service;

import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.vo.reply.ReplyVO;
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
        for(int i = 0; i < 100; i ++) {
            ReplyDTO replyDTO = ReplyDTO.builder()
                    .content("댓글 서비스 등록 테스트" + i)
                    .userId("admin")
                    .nickname("admin")
                    .boardId(200).build();
            replyService.addReply(replyDTO);
        }
    }

    // 여기서부터 대댓글 때문에 추가/수정된 부분
    @Test
    public void addReReply() { // 대댓글 추가
        for(int i = 0; i < 30; i ++) {
            ReplyDTO replyDTO = ReplyDTO.builder()
                    .content("대댓글 서비스 등록 테스트" + i)
                    .userId("eeee8888")
                    .nickname("eeee")
                    .boardId(109)
                    .replyId(322)
                    .build();
            replyService.addReReply(replyDTO);
        }
    }

    @Test
    public void testRemoveReReply() { // 대댓글 삭제
        replyService.removeReReply(385);
    }

    @Test
    public void testRemoveReply() { // 원 댓글 논리 삭제
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(321)
                .build();
        replyService.removeReply(replyDTO);
    }
    // 여기까지 대댓글 때문에 추가/수정된 부분

    @Test
    public void getReply() {
        ReplyDTO replyDTO = replyService.getReply(7);
        log.info(replyDTO);
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
//        List<ReplyDTO> dtoList=replyService.getReplyList(109);
//        dtoList.forEach(dto -> log.info(dto));
//    }

    @Test
    public void testGetCount() {
        log.info(replyService.getReplyCount(1));
    }

}
