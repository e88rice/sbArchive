package com.project.sbarchive.dto.signboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignBoardAllDTO {

    private int signboardId;
    private String userId;
    private String xOffSet;
    private String yOffSet;
    private String title;
    private String address;
    private String content;
    private LocalDateTime addDate;
    private LocalDateTime modDate;

    ArrayList<String> files;

}
