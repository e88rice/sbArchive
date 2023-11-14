package com.project.sbarchive.mapper.user;

import com.project.sbarchive.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 회원 가입
    void registerUser(UserVO userVO);
    void addUserRole(String userId, Integer role_set);

    // 이메일 유효성 검사
    int emailCheck(String email);

    // 아이디 유효성 검사
    int userIdCheck(String userId);

    // 로그인
    UserVO getUserInfo(String userId);

}
