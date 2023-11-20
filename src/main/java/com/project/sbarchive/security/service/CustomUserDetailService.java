package com.project.sbarchive.security.service;


import com.project.sbarchive.security.dto.MemberSecurityDTO;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserRole;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor // 추가
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: "+username);

        UserVO member = userService.getUserInfo(username);
        Set<UserRole> roles = member.getRoleSet(); // 0, 1로 구성 된 열거형 UserRole의 인덱스 번호를 가진 리스트
        Set<UserRole> newRoles = new HashSet<>(); // 해당 인덱스 번호로 열거형 UserRole 값을 가져와서 저장하는 리스트
        for(int i=0; i<roles.size(); i++){
            UserRole test = UserRole.values()[i];
            newRoles.add(test);
        }

        member.setRoleSet(newRoles);

        if(member == null || member.isDel()) { // 해당 아이디를 가진 사용자가 없다면
            throw new UsernameNotFoundException("username not found...");
        }

        log.info("===============================================");
        log.info( member.getRoleSet().stream()
                .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                .collect(Collectors.toList()));

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getUserId(), member.getPasswd(), member.getNickname(), member.getEmail(), member.getLevel(),
                member.getLvPoint(), member.getIconName(), member.getRegDate(), member.isDel(), false,
                member.getRoleSet().stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );
        log.info("memberSecurityDTO.getAuthorities() : " + memberSecurityDTO.getAuthorities());
        log.info("memberSecurityDTO : " + memberSecurityDTO);
        return memberSecurityDTO;
    }

}