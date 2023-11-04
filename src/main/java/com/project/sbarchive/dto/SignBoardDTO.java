package com.project.sbarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardDTO {

    private int index;
    private String xOffSet;
    private String yOffSet;
    private String name;
    private String address;

}
