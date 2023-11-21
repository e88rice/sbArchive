package com.project.sbarchive.controller.admin;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.admin.AdminService;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService adminService;

    @GetMapping("/get/user/{page}")
    public PageResponseDTO<UserDTO> getUsers(@PathVariable("page") int page) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(10).build();
        return adminService.getUsers(pageRequestDTO);
    }

    @GetMapping("/get/signboard/{page}")
    public PageResponseDTO<SignBoardDTO> getSignboards(@PathVariable("page") int page) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(10).build();
        return adminService.getSignboards(pageRequestDTO);
    }

    @GetMapping("/get/board/{page}")
    public PageResponseDTO<BoardDTO> getBoards(@PathVariable("page") int page) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(10).build();
        return adminService.getBoards(pageRequestDTO);
    }

    @GetMapping("/get/replies/{page}")
    public PageResponseDTO<ReplyDTO> getReplies(@PathVariable("page") int page) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(10).build();
        return adminService.getReplies(pageRequestDTO);
    }

    @DeleteMapping("/remove/{type}/{typeId}")
    public void removeFromManager(@PathVariable("type") String type, @PathVariable("typeId") String typeId) {
        if(type.equals("user")) {
            adminService.removeUserFromManger(typeId);
        } else {
            adminService.removeFromManager(type, Integer.parseInt(typeId));
        }
    }
}
