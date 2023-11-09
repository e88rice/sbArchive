package com.project.sbarchive.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String userId;
    private String passwd;
    private String nickname;
    private String email;
    private int level;
    private int lvPoint;
    private String iconName;
    private LocalDate regDate;
}
