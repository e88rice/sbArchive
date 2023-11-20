package com.project.sbarchive.service.reply;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.vo.reply.ReplyVO;

import java.util.List;

public interface ReplyService {

    // 댓글 추가
    int addReply(ReplyDTO replyDTO);

    // 대댓글 추가
    int addReReply(ReplyDTO replyDTO);

    // 댓글 가져오기
    ReplyDTO getReply(int replyId);

    // parentReplyId에 replyId값 넣기
    void modifyReplyId(ReplyDTO replyDTO);

    // 댓글 수정
    void modifyReply(ReplyDTO replyDTO);

    // 댓글 논리 삭제
     void removeReply(ReplyDTO replyDTO);

    // 대댓글 삭제
    void removeReReply(int replyId);

    // 원댓글 목록 가져오기
    PageResponseDTO<ReplyDTO> getReplyList(int boardId, boolean replyDepth, PageRequestDTO pageRequestDTO);

    // 대댓글 목록 가져오기
    List<ReplyDTO> getReReplies(int boardId, int parentReplyId, boolean replyDepth);

    // 댓글 개수
    int getReplyCount(int boardId);

    // 대댓글 개수
    int countReReplies(int parentReplyId);





    // 여기서부터 Board에서 replyCount 나타낼 때 사용하는 메서드
    void upReplyCount(int boardId);

    void downReplyCount(int boardId);

}
