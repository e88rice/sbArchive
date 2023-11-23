package com.project.sbarchive.mapper.reply;

import com.project.sbarchive.vo.reply.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    // 원댓글 추가
    void addReply(ReplyVO replyVO);

    // addReply 후 parentReplyId에 replyId값 넣어주기
    void modifyReplyId(ReplyVO replyVO);

    // 원댓글 가져오기
    ReplyVO getReply(int replyId);

    // 원댓글 수정
    void modifyReply(ReplyVO replyVO);

    // 대댓글 삭제
    void removeReReply(int replyId);

    // 원댓글 논리 삭제
    void removeReply(ReplyVO replyVO);

    // 원댓글 목록 가져오기
    List<ReplyVO> getReplyList(int boardId, boolean replyDepth, int skip, int size);

    // 대댓글 목록 가져오기
    List<ReplyVO> getReReplies(int boardId, int parentReplyId, boolean replyDepth);

    // 원댓글 개수
    int getReplyCount(int boardId);

    // 대댓글 추가
    void addReReply(ReplyVO replyVO);

    // 대댓글의 개수
    int countReReplies(int parentReplyId);

    // 댓글이 신고 처리됐을 때, '관리자에 의해 규제된 댓글입니다' 문구 출력
    void reportedReply(ReplyVO replyVO);






    // 여기서부터 Board에서 replyCount 나타낼 때 사용하는 메서드
    void upReplyCount(int boardId);

    void downReplyCount(int boardId);
}
