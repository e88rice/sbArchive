package com.project.sbarchive.dto.signboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardDTO {

    private int singboardId;
    private String userId;
    private String xOffSet;
    private String yOffSet;
    private String title;
    private String address;
    private String content;
    private LocalDateTime addDate;
    private LocalDateTime modDate;

}
