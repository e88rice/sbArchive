package com.project.sbarchive.service;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
