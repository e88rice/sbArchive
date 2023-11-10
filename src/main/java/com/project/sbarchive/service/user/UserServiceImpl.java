package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.mapper.user.UserMapper;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    @Override
    public void registerUser(UserDTO userDTO) {
        log.info("===== registerUser Service =====");
        UserVO userVO = modelMapper.map(userDTO, UserVO.class);
        userMapper.registerUser(userVO);
    }

    @Override
    public int emailCheck(String email) {
        log.info("===== emailCheck Service =====");
        int cnt = userMapper.emailCheck(email);
        log.info("cnt : " + cnt);
        return cnt;
    }

    @Override
    public int userIdCheck(String userId) {
        log.info("===== userIdCheck Service =====");
        int cnt = userMapper.userIdCheck(userId);
        log.info("cnt : " + cnt);
        return cnt;
    }

    @Override
    public int loginCheck(String userId, String passwd) {
        log.info("===== loginCheck Service =====");
        return  userMapper.loginCheck(userId, passwd);
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        UserVO userVO = userMapper.getUserInfo(userId);
        return modelMapper.map(userVO, UserDTO.class);
    }

    @Override
    public void modifyUuid(String userId, String uuid) {

    }


}
