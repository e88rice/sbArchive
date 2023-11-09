package com.project.sbarchive.mapper.user;

import com.project.sbarchive.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void registerUser(UserVO userVO);
    public int emailCheck(String email);
    public int userIdCheck(String userId);
}
