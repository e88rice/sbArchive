package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@Log4j2
@SpringBootTest
class SignBoardMapperTest {

    @Autowired(required = false)
    private SignBoardMapper signBoardMapper;

    @Test
    void getSearchList() {
        ArrayList<SignBoardAllDTO> dtoList = signBoardMapper.getSearchSignboardList("서울특별시 중구");
        dtoList.forEach(dto -> log.info(dto));
    }
}