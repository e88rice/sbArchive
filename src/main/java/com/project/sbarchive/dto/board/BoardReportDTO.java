package com.project.sbarchive.dto.board;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BoardReportDTO {
    int rBoardId;

    String userId;
    String title;
    String content;
    int hit;
    int replyCount;
    boolean isAnswered;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDate addDate;
}
