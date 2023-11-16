package com.project.sbarchive.service.message;

import com.project.sbarchive.dto.message.MessageDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.vo.message.MessageVO;

import java.util.List;

public interface MessageService {

    // 쪽지 보내기
    void add(String senderId, String receiverId, String content);

    // 받은 쪽지함
    PageResponseDTO<MessageDTO> getAllMsgByReceiver(String receiverId, PageRequestDTO pageRequestDTO);

    // 받은 쪽지의 총 개수
    int getAllMsgByReceiverCount(String receiverId);

    // 보낸 쪽지함
    PageResponseDTO<MessageDTO> getAllMsgBySender(String senderId, PageRequestDTO pageRequestDTO);

    // 보낸 쪽지의 총 개수
    int getAllMsgBySenderCount(String senderId);

    // 쪽지 읽기
    MessageDTO getMessage(int index);

    // 받은 쪽지함에서 쪽지 읽기를 했다면 읽음 처리 및 readDate 기록
    void checkMessage(int index);

    // 받은 쪽지함에서 삭제 처리했을 경우 isReceiverDeleted 를 true 로 처리
    void removeByReceiver(int index);

    // 보낸 쪽지함에서 삭제 처리했을 경우 isSenderDeleted 를 true 로 처리
    void removeBySender(int index);

}
