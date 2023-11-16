package com.project.sbarchive.vo.user;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {
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

    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();

    public void changeMid(String userId) {
        this.userId = userId;
    }

    public void changeMpw(String passwd) {
        this.passwd = passwd;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeDel(boolean del) {
        this.del = del;
    }

    public void changeSocial(boolean social) {
        this.social = social;
    }

    public void addRole(UserRole userRole) {
        this.roleSet.add(userRole);
    }

    public void setRoleSet(Set<UserRole> roleSet) {
        this.roleSet = roleSet;
    }

    public void clearRole() {
        this.roleSet.clear();
    }

    @Builder.Default
    private Set<UserPwLog> passwordLog = new HashSet<>();

    public void addLog(UserPwLog userPwLog){
        this.passwordLog.add(userPwLog);
    }

    public void updateLog(Set<UserPwLog> passwordLog) {
        this.passwordLog = passwordLog;
    }
}
