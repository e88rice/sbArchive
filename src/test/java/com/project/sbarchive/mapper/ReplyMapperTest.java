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

//    @Test
//    public void addReply() {
//        replyMapper.add(ReplyVO.builder()
//                        .content("댓글 등록")
//                        .boardId(1)
//                        .userId("admin")
//                        .nickname("admin").build());
//    }

    @Test
    public void getReply() {
        ReplyVO replyVO=replyMapper.getReply(1);
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
    public void removeReply() {
        replyMapper.removeReply(1);
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


}
