package com.project.sbarchive.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    @NotEmpty
    private Integer boardId;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String title;
    private String content;
    private Integer hit;
    private Integer likeUp;
    private Integer replyCount;
    private LocalDateTime addDate;
    private LocalDateTime modDate;
}