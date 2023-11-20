package com.project.sbarchive.vo.board;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardLikeVO {

    int likeId;
    int boardId;
    String userId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;
}
