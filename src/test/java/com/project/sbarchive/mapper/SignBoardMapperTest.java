package com.project.sbarchive.mapper;

import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class SignBoardMapperTest {

    @Autowired(required = false)
    private SignBoardMapper signBoardMapper;

    @Test
    void register() {
        signBoardMapper.register(SignBoardVO.builder()
                .xOffSet("35.8661170068962")
                .yOffSet("128.593835998552")
                .name("코리아 IT 아카데미 대구지점")
                .address("대구광역시 중구 중앙대로 366").build());

    }
}