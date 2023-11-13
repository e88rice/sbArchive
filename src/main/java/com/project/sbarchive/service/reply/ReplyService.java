package com.project.sbarchive.service.reply;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.vo.reply.ReplyVO;

import java.util.List;

public interface ReplyService {

    // 댓글 추가
    int addReply(ReplyDTO replyDTO);

    // 댓글 가져오기
    ReplyDTO getReply(int replyId);

    // 댓글 수정
    void modifyReply(ReplyDTO replyDTO);

    // 댓글 삭제
    void removeReply(int replyId);

    // 댓글 목록 가져오기
    PageResponseDTO<ReplyDTO> getReplyList(int boardId, PageRequestDTO pageRequestDTO);

    // 댓글 개수
    int getReplyCount(int boardId);

}
