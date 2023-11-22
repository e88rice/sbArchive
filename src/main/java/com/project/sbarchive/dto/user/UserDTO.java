package com.project.sbarchive.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String userId;
    private String passwd;
    private String nickname;
    private String email;
    private int level;
    private int lvPoint;
    private String iconName;
    private LocalDate regDate;
    private boolean del;
    private boolean social;

}
