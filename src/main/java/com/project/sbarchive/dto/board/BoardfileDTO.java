package com.project.sbarchive.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardfileDTO {
    private int index;
    private int boardId;
    private String fileName;

}
