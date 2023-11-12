package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class SignboardMapperTest {

    @Autowired(required = false)
    private SignBoardMapper signBoardMapper;

    @Test
    void register() {
        for(int i=0; i<=100; i++) {
            signBoardMapper.addSignboard(SignBoardVO.builder()
                    .userId("admin")
                    .xOffSet("128.593835998552")
                    .yOffSet("35.8661170068962")
                    .title("코리아 IT 아카데미 대구지점"+i)
                    .address("대구광역시 중구 중앙대로 366")
                    .content("테스트.."+i).build());
        }

    }

    @Test
    void getSignboardListWithPaging() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).size(10).build();
        signBoardMapper.getSignboardListWithPaging(pageRequestDTO).forEach(signBoardAllDTO -> {
            log.info(signBoardAllDTO);
        });
    }
}