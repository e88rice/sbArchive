package com.project.sbarchive.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeDTO {
    int likeId;
    int boardId;
    String userId;
    String title;


    private LocalDateTime addDate;
}
