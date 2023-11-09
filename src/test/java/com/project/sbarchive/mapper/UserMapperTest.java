package com.project.sbarchive.mapper;

import com.project.sbarchive.mapper.user.UserMapper;
import com.project.sbarchive.vo.user.UserVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class UserMapperTest {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void registerTest() {
        UserVO userVO = UserVO.builder()
                .userId("mapper")
                .passwd("1111")
                .nickname("mapper")
                .email("mapper@mapper.com")
                .build();
        userMapper.registerUser(userVO);
    }
}
