package com.project.sbarchive.service.signboard;

import com.project.sbarchive.dto.signboard.SignBoardDTO;

import java.util.ArrayList;

public interface SignBoardService {
    void register(SignBoardDTO signBoardDTO);

    ArrayList<SignBoardDTO> getList();
}
