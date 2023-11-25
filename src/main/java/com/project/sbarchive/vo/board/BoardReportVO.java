package com.project.sbarchive.vo.board;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardReportVO {
    int rBoardId;
    int boardId;
    int replyId;
    String userId;
    String title;
    String content;
    int isAnswered;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDate addDate;
}
