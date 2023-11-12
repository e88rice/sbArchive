package com.project.sbarchive.vo.signboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignBoardVO {

    private int signboardId;
    private String userId;
    private String nickname;
    private String xOffSet;
    private String yOffSet;
    private String title;
    private String address;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;
}
