package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.vo.user.UserVO;

import java.util.List;

public interface UserService {
    // 회원 가입
    void registerUser(UserDTO userDTO);
    void addUserRole(String userId, List<Integer> role_set);

    // 이메일 유효성 검사
    int emailCheck(String email);

    // 아이디 유효성 검사
    int userIdCheck(String userId);

    // 로그인
    UserVO getUserInfo (String userId);

    // 아이디 찾기
    String getUserId (String email);

}
