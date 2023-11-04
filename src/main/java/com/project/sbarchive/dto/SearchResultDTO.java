package com.project.sbarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDTO {

    private String title;
    private String link;
    private String category;
    private String address;
    private String xOffSet;
    private String yOffSet;

}
