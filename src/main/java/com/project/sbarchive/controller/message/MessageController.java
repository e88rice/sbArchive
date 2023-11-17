package com.project.sbarchive.controller.message;

import com.project.sbarchive.dto.message.MessageDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/message")
@Log4j2
@RequiredArgsConstructor // 의존성 주입 위해
public class MessageController {

    private final MessageService messageService;

    // 받은 쪽지함 페이지
    @PreAuthorize("principal.username == #receiverId")
    @GetMapping("/getReceivedMsg/{page}/{receiverId}")
    public PageResponseDTO<MessageDTO> getMsgByReceived(
            @PathVariable("page") int page, @PathVariable("receiverId") String receiverId) {

        // 페이지를 받아서 PageRequestDTO 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(7).build();

        // 페이징된 객체 리스트를 받아옴
        PageResponseDTO<MessageDTO> responseDTO = messageService.getAllMsgByReceiver(receiverId, pageRequestDTO);
        responseDTO.getDtoList().forEach(messageDTO -> log.info(messageDTO));

        return responseDTO;
    }

    // 보낸 쪽지함 페이지
    @PreAuthorize("principal.username == #senderId")
    @GetMapping("/getSentMsg/{page}/{senderId}")
    public PageResponseDTO<MessageDTO> getMsgBySent(
            @PathVariable("page") int page, @PathVariable("senderId") String senderId) {

        // 페이지를 받아서 PageRequestDTO 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(7).build();

        // 페이징된 객체 리스트를 받아옴
        PageResponseDTO<MessageDTO> responseDTO = messageService.getAllMsgBySender(senderId, pageRequestDTO);
        log.info("====================== 보낸 쪽지함 ==========================");
        responseDTO.getDtoList().forEach(messageDTO -> log.info(messageDTO));

        return responseDTO;
    }

    // 메세지 삭제
    @DeleteMapping("/remove/{index}/{type}")
    public void removeMsg(@PathVariable("index") int index, @PathVariable("type") String type) {
        if(type.equals("sender")) { // 받은 쪽지함에서 전달된 거라. 리시버 삭제 여부를 true로 전환
            messageService.removeByReceiver(index);
        } else { // 보낸 쪽지함에서 전달된 거.
            messageService.removeBySender(index); // Sender 기준 삭제 여부 true로 전환
        }
    }

    @GetMapping("/getUsername")
    public ArrayList<String> getUsername(Principal principal) {
        ArrayList<String> msgUserId = new ArrayList<>();
        if (principal != null) {
            msgUserId.add(principal.getName());
        } else {
            msgUserId.add("null");
        }
        return msgUserId;
    }
}
