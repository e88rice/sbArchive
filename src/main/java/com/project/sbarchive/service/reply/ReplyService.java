package com.project.sbarchive.service.reply;

import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.vo.reply.ReplyVO;

import java.util.List;

public interface ReplyService {

    // 댓글 추가
    int add(ReplyDTO replyDTO);

    // 댓글 가져오기
    ReplyDTO getReply(int replyId);

    // 댓글 수정
    void modify(ReplyDTO replyDTO);

    // 댓글 삭제
    void remove(int replyId);

    // 댓글 목록 가져오기
    List<ReplyDTO> getReplyList(int boardId);

    // 댓글 개수
    int getReplyCount(int boardId);

}
