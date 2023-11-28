package com.project.sbarchive.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String userId;

    @NotBlank
    private String passwd;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,8}$")
    private String nickname;

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
    @NotBlank
    private String email;

    private int level;
    private int lvPoint;
    private String iconName;
    private LocalDate regDate;
    private boolean del;
    private boolean social;

}
