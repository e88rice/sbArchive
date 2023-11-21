package com.project.sbarchive.service.admin;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.mapper.admin.AdminMapper;
import com.project.sbarchive.vo.board.BoardVO;
import com.project.sbarchive.vo.reply.ReplyVO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<UserDTO> getUsers(PageRequestDTO pageRequestDTO) {
        List<UserDTO> dtoList = adminMapper.getUsers(pageRequestDTO).stream().map(vo -> modelMapper.map(vo, UserDTO.class)).collect(Collectors.toList());

        int total = adminMapper.getUsersCount();

        return PageResponseDTO.<UserDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getUsersCount() {
        return adminMapper.getUsersCount();
    }

    @Override
    public PageResponseDTO<SignBoardDTO> getSignboards(PageRequestDTO pageRequestDTO) {
        List<SignBoardDTO> dtoList = adminMapper.getSignboards(pageRequestDTO).stream().map(vo -> modelMapper.map(vo, SignBoardDTO.class)).collect(Collectors.toList());

        int total = adminMapper.getSignboardsCount();

        return PageResponseDTO.<SignBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getSignboardsCount() {
        return adminMapper.getSignboardsCount();
    }

    @Override
    public PageResponseDTO<BoardDTO> getBoards(PageRequestDTO pageRequestDTO) {
        List<BoardDTO> dtoList = adminMapper.getBoards(pageRequestDTO).stream().map(vo -> modelMapper.map(vo, BoardDTO.class)).collect(Collectors.toList());

        int total = adminMapper.getBoardsCount();

        return PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getBoardsCount() {
        return adminMapper.getBoardsCount();
    }

    @Override
    public PageResponseDTO<ReplyDTO> getReplies(PageRequestDTO pageRequestDTO) {
        List<ReplyDTO> dtoList = adminMapper.getReplies(pageRequestDTO).stream().map(vo -> modelMapper.map(vo, ReplyDTO.class)).collect(Collectors.toList());

        int total = adminMapper.getRepliesCount();

        return PageResponseDTO.<ReplyDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getRepliesCount() {
        return adminMapper.getRepliesCount();
    }

    @Override
    public void removeUserFromManger(String userId) {
        adminMapper.removeUserFromManger(userId);
    }

    @Override
    public void removeFromManager(String type, int typeId) {
        adminMapper.removeFromManager(type, typeId);
    }
}
