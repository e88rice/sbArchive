package com.project.sbarchive.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardVO {

    private int index;
    private String xOffSet;
    private String yOffSet;
    private String name;
    private String address;
}
