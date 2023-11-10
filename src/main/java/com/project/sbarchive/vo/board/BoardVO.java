package com.project.sbarchive.vo.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private Integer boardId;
    private String userId;
    private String title;
    private String content;
    private Integer hit;
    private Integer likeUp;
    private Integer replyCount;
    private LocalDateTime addDate;
    private LocalDateTime modDate;
}
