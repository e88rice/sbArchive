package com.project.sbarchive.service.signboard;

import com.project.sbarchive.dto.signboard.SignBoardDTO;

import java.util.ArrayList;

public interface SignBoardService {

    int add(SignBoardDTO signBoardDTO);

    ArrayList<SignBoardDTO> getList();

}
