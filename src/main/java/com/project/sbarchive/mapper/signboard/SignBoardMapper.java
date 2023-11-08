package com.project.sbarchive.mapper.signboard;

import com.project.sbarchive.vo.signboard.SignBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SignBoardMapper {
    void register(SignBoardVO signBoardVO);

    ArrayList<SignBoardVO> getList();
}
