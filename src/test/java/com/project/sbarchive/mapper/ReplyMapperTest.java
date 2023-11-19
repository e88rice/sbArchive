package com.project.sbarchive.mapper;

import com.project.sbarchive.mapper.reply.ReplyMapper;
import com.project.sbarchive.vo.reply.ReplyVO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class ReplyMapperTest {

    @Autowired(required = false)
    private ReplyMapper replyMapper;

    @Test
    public void addReply() {
        replyMapper.addReply(ReplyVO.builder()
                        .content("댓글 등록")
                        .boardId(1)
                        .userId("admin")
                        .nickname("admin").build());
    }

    @Test
    public void getReply() {
        ReplyVO replyVO=replyMapper.getReply(320);
        log.info("1번째 댓글의 내용은: "+replyVO);
    }

    @Test
    public void modifyReply() {
        ReplyVO replyVO = ReplyVO.builder()
                .replyId(1)
                .content("내용 수정함")
                .build();
        replyMapper.modifyReply(replyVO);
    }

    @Test
    public void removeReply() { // 논리 삭제
        ReplyVO replyVO = ReplyVO.builder()
                .replyId(320)
                .build();
        replyMapper.removeReply(replyVO);
    }

    @Test
    public void removeReReply() {
        replyMapper.removeReReply(325);
    }

//    @Test
//    public void getReplyList() {
//        List<ReplyVO> voList=replyMapper.getReplyList(1);
//        voList.forEach(vo -> log.info(vo));
//    }

    @Test
    public void getReplyCount() {
        log.info(replyMapper.getReplyCount(1));
    }






    // 대댓글 영역
    // 대댓글 추가
    @Test
    public void addReReply() {
        replyMapper.addReReply(
                ReplyVO.builder()
                        .content("대댓글 등록")
                        .boardId(109)
                        .userId("abab")
                        .nickname("abab")
                        .parentReplyId(320)
                        .build()
        );
    }


}
