package com.project.sbarchive.vo.reply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyVO {

    // 댓글 번호
    private int replyId;

    // 보드 번호
    @NotNull
    private int boardId;


    // 유저 아이디
    @NotNull
    private String userId;

    // 유저 닉네임
    @NotNull
    private String nickname;

    // 댓글 내용
    @NotEmpty
    @Min(2)
    @Max(300)
    private String content;

    // 댓글 등록 날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    // 댓글 수정 날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;



    // 대댓글 관련
    // 댓글 깊이. 0(댓글), 1(대댓글). default 0
    private boolean replyDepth;


    // (대댓글이 달린다면) 대댓글의 부모 replyId
    private int parentReplyId;

    // 원 댓글 삭제 여부
    private boolean isParentReplyDeleted;

    // 대댓글 개수
    private int reReplyCount;

    // 신고 처리당한 댓글일 때, '관리자에 의해 규제된 댓글입니다' 문구 출력
    private boolean isReported;
}
