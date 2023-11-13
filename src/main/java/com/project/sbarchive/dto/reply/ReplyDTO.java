package com.project.sbarchive.dto.reply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

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
    private String content;

    // 댓글 등록 날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    // 댓글 수정 날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

}
