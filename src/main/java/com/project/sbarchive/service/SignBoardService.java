package com.project.sbarchive.service;

import com.project.sbarchive.dto.SignBoardDTO;

import java.util.ArrayList;
import java.util.List;

public interface SignBoardService {
    void register(SignBoardDTO signBoardDTO);

    ArrayList<SignBoardDTO> getList();
}
