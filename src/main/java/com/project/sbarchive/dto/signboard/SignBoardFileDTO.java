package com.project.sbarchive.dto.signboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardFileDTO {

    private int index;
    private int signboardId;
    private String fileName;

}
