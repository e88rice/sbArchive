package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.vo.user.UserVO;

import java.util.List;

public interface UserService {
    // 회원 가입
    void registerUser(UserDTO userDTO);
    void addUserRole(String userId, List<Integer> role_set);
    void addLog(String userId, String passwd);

    // 이메일 유효성 검사
    int emailCheck(String email);

    // 아이디 유효성 검사
    int userIdCheck(String userId);

    // 로그인
    UserVO getUserInfo (String userId);

    // 아이디 찾기
    String getUserId (String email);

    // 비밀번호 확인
    boolean isConfirmPassword (String userId, String passwd);

    // 비밀번호 찾기 (아이디 이메일 일치 확인)
    int accountCheck(String userId, String email);

    // 임시 비밀번호 발급
    void updateTempPassword (String userId, String passwd);
    // 비밀번호 임시 재설정
    void updateLogTemp (String userId, String passwd);

    // 임시 비밀번호인지 확인
    int isTempPassword(String userId);
    // 비밀번호 재설정
    void updatePassword (String userId, String passwd);
    void updateLog (String userId, String passwd);

    // 닉네임 재설정
    void modifyNickname(String userId, String email, String nickname);
}
