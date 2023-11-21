package com.project.sbarchive.mapper.admin;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.board.BoardVO;
import com.project.sbarchive.vo.reply.ReplyVO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import com.project.sbarchive.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVO> getUsers(PageRequestDTO pageRequestDTO);
    int getUsersCount();

    List<SignBoardVO> getSignboards(PageRequestDTO pageRequestDTO);
    int getSignboardsCount();

    List<BoardVO> getBoards(PageRequestDTO pageRequestDTO);
    int getBoardsCount();

    List<ReplyVO> getReplies(PageRequestDTO pageRequestDTO);
    int getRepliesCount();

    void removeUserFromManger(String userId);
    void removeFromManager(String type, int typeId);
}
