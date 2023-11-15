package com.project.sbarchive.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

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



    public MemberSecurityDTO(String username, String password, String nickname, String email, int level, int lvPoint,
                             String iconName, LocalDate regDate, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        log.info("@@@@@@@@@@@@@@@@@@");
        log.info(authorities);
        this.userId = username;
        this.passwd = password;
        this.nickname = nickname;
        this.email = email;
        this.level = level;
        this.lvPoint = lvPoint;
        this.iconName = iconName;
        this.regDate = regDate;
        this.del = del;
        this.social = social;
    }
}
