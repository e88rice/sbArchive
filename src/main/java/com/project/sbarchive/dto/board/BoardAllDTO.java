package com.project.sbarchive.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardAllDTO {

    private Integer boardId;

    private int rBoardId;

    private int replyId;
    @NotEmpty
    private String userId;

    private int likeId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String nickname;
    private String content;
    private Integer hit;
    private Integer likeUp;
    private Integer replyCount;

    private LocalDateTime addDate;

    private LocalDateTime modDate;

    private LocalDateTime delDate;

    int isAnswered;



    //image --
    private int index;
    ArrayList<String> files;
}
