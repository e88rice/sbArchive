package com.project.sbarchive.vo.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileVO {

    private int index;
    private int boardId;
    private String fileName;

}
