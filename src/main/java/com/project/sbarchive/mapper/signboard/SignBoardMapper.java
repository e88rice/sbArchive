package com.project.sbarchive.mapper.signboard;

import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SignBoardMapper {
    void add(SignBoardVO signBoardVO);

    ArrayList<SignBoardAllDTO> getList();
}
