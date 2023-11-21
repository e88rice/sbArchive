package com.project.sbarchive.mapper;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.mapper.admin.AdminMapper;
import com.project.sbarchive.vo.board.BoardVO;
import com.project.sbarchive.vo.reply.ReplyVO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import com.project.sbarchive.vo.user.UserVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class AdminMapperTests {

    @Autowired(required = false)
    private AdminMapper adminMapper;

    @Test
    public void getUsers() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        List<UserVO> userVOList = adminMapper.getUsers(pageRequestDTO);
        userVOList.forEach( vo -> log.info(vo));
    }

    @Test
    public void getSignboards() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        List<SignBoardVO> userVOList = adminMapper.getSignboards(pageRequestDTO);
        userVOList.forEach( vo -> log.info(vo));
    }

    @Test
    public void getBoards() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        List<BoardVO> userVOList = adminMapper.getBoards(pageRequestDTO);
        userVOList.forEach( vo -> log.info(vo));
    }

    @Test
    public void getReplies() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        List<ReplyVO> userVOList = adminMapper.getReplies(pageRequestDTO);
        userVOList.forEach( vo -> log.info(vo));
    }

    @Test
    public void removeFromManger() {
        adminMapper.removeFromManager("board", 4);
        adminMapper.removeFromManager("reply", 15);
    }
}
