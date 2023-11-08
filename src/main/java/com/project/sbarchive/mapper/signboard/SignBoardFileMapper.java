package com.project.sbarchive.mapper.signboard;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignBoardFileMapper {

    void add(int signboardId, String fileName);

}
