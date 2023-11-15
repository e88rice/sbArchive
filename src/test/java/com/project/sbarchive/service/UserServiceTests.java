package com.project.sbarchive.service;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
public class UserServiceTests {
    @Autowired(required = false)
    private UserService userService;

    @Test
    public void testRegister() {
        UserDTO userDTO = UserDTO.builder()
                .userId("service")
                .passwd("2222")
                .nickname("service")
                .email("service@service.com")
                .build();
        userService.registerUser(userDTO);
    }

    private PasswordEncoder passwordEncoder;

    @Test
    public void registerUserTest() {
        passwordEncoder = new BCryptPasswordEncoder();
        String userId = "member";
        UserDTO userDTO = UserDTO.builder()
                .userId(userId)
                .passwd("1111")
                .nickname("member")
                .email("mem@bbb.com")
                .del(false)
                .social(false).build();
        List<Integer> role_set = new ArrayList<>();
        role_set.add(0);
        role_set.add(1);
        userService.registerUser(userDTO);
        userService.addUserRole(userId, role_set);
    }
}
