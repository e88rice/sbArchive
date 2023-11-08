package com.project.sbarchive.vo.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyVO {
    private Integer replyId; //리플 고유번호

    @NotNull
    private Integer boardId; //게시한의 글 고유번호

    @NotEmpty
    private String nickname; //리플 작성자

    @NotEmpty
    private String content; // 리플 내용


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;
}
