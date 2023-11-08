package com.project.sbarchive.vo.signboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardFileVO {

    private int index;
    private int signboardId;
    private String fileName;

}
