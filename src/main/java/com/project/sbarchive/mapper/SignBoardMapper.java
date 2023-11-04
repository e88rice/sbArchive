package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.SignBoardDTO;
import com.project.sbarchive.vo.SignBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SignBoardMapper {
    void register(SignBoardVO signBoardVO);

    ArrayList<SignBoardVO> getList();
}
