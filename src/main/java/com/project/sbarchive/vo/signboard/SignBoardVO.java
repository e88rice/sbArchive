package com.project.sbarchive.vo.signboard;

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
    private String xOffSet;
    private String yOffSet;
    private String title;
    private String address;
    private String content;
    private LocalDateTime addDate;
    private LocalDateTime modDate;
}
