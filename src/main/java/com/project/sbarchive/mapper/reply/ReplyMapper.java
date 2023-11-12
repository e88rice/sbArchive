package com.project.sbarchive.mapper.reply;

import com.project.sbarchive.vo.reply.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 댓글 추가
    void add(ReplyVO replyVO);

    // 댓글 가져오기
    ReplyVO getReply(int replyId);

    // 댓글 수정
    void modify(ReplyVO replyVO);

    // 댓글 삭제
    void remove(int replyId);

    // 댓글 목록 가져오기
    List<ReplyVO> getReplyList(int boardId, int skip, int size);

    // 댓글 개수
    int getReplyCount(int boardId);

}
