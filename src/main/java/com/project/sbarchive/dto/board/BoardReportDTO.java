package com.project.sbarchive.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReportDTO {
    int rBoardId;
    String userId;
    String title;
    String content;

    boolean isAnswered;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDate addDate;
}
