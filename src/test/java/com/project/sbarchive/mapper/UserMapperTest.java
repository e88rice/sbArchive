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

    // 삭제요망_현준
    @Test
    public void test() {
        log.info(userMapper.checkDupl("guswns8780@naver.com"));
        log.info(userMapper.getUserInfoByEmail("guswns8780@naver.com"));
    }

    @Test
    public void getUserInfoTest() {
        String userId = "member1";
        UserVO userVO = userMapper.getUserInfo(userId);

        log.info(userVO);
    }

    @Test
    public void modifyEmailTest() {
        String userId = "aaa222";
        String email = "aaa@222.com";

        userMapper.modifyEmail(userId, email);
    }

    @Test
    public void myLikedList() {
        String userId = "plez1992";

        log.info(userMapper.getMyLikedList(userId, 0, 10, null, null));
    }
}
