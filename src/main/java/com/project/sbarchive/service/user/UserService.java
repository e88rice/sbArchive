package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.user.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);
    int emailCheck(String email);
    int userIdCheck(String userId);

}
