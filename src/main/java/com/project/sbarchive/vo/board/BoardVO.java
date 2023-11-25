package com.project.sbarchive.vo.board;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private Integer boardId;
    private String userId;
    private String nickname;
    private String title;
    private String content;
    private Integer hit;
    private Integer likeUp;
    private Integer replyCount;

    private LocalDateTime addDate;

    private LocalDateTime modDate;

    private LocalDateTime delDate;
}
