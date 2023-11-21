package com.project.sbarchive.service.admin;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;


public interface AdminService {

    PageResponseDTO<UserDTO> getUsers(PageRequestDTO pageRequestDTO);
    int getUsersCount();

    PageResponseDTO<SignBoardDTO> getSignboards(PageRequestDTO pageRequestDTO);
    int getSignboardsCount();

    PageResponseDTO<BoardDTO> getBoards(PageRequestDTO pageRequestDTO);
    int getBoardsCount();

    PageResponseDTO<ReplyDTO> getReplies(PageRequestDTO pageRequestDTO);
    int getRepliesCount();

    void removeUserFromManger(String userId);
    void removeFromManager(String type, int typeId);

}
