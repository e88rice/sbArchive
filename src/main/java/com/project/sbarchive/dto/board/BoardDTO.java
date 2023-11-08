package com.project.sbarchive.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Integer boardId;
    private String userId;
    private String title;
    private String content;
    private Integer hit;
    private Integer like;
    private Integer replyCount;
    private LocalDateTime addDate;
    private LocalDateTime modDate;
}