package com.project.sbarchive.mapper.user;

import com.project.sbarchive.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(UserVO userVO);
    int emailCheck(String email);
    int userIdCheck(String userId);

    int loginCheck(String userId, String passwd);
    UserVO getUserInfo(String userId);
}
