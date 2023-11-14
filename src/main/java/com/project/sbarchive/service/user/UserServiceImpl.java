package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.mapper.user.UserMapper;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    @Override
    public void registerUser(UserDTO userDTO) {
        log.info("============= registerUser Service =============");
        UserVO userVO = modelMapper.map(userDTO, UserVO.class);
        userMapper.registerUser(userVO);
    }

    @Override
    public void addUserRole(String userId, List<Integer> role_set) {
        log.info("============= addUserRole Service =============");
        for(int i=0; i<role_set.size(); i++) {
            userMapper.addUserRole(userId, role_set.get(i));
        }
    }

    @Override
    public int emailCheck(String email) {
        log.info("============= emailCheck Service =============");
        int cnt = userMapper.emailCheck(email);
        log.info("cnt : " + cnt);
        return cnt;
    }

    @Override
    public int userIdCheck(String userId) {
        log.info("============= userIdCheck Service =============");
        int cnt = userMapper.userIdCheck(userId);
        log.info("cnt : " + cnt);
        return cnt;
    }


    @Override
    public UserVO getUserInfo(String userId) {
        log.info("============= getUserInfo Service =============");
        return userMapper.getUserInfo(userId);
    }


}
