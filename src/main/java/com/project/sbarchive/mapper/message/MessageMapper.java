package com.project.sbarchive.mapper.message;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.vo.message.MessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessageMapper {

    // 쪽지 보내기
    void add(String senderId, String receiverId, String content);

    // 받은 쪽지함
    List<MessageVO> getAllMsgByReceiver(String receiverId, int skip, int size);

    // 받은 쪽지의 총 개수
    int getAllMsgByReceiverCount(String receiverId);

    // 보낸 쪽지함
    List<MessageVO> getAllMsgBySender(String senderId, int skip, int size);

    // 보낸 쪽지의 총 개수
    int getAllMsgBySenderCount(String senderId);

    // 쪽지 읽기
    MessageVO getMessage(int index);

    // 받은 쪽지함에서 쪽지 읽기를 했다면 읽음 처리 및 readDate 기록
    void checkMessage(int index);

    // 받은 쪽지함에서 삭제 처리했을 경우 isReceiverDeleted 를 true 로 처리
    void removeByReceiver(int index);

    // 보낸 쪽지함에서 삭제 처리했을 경우 isSenderDeleted 를 true 로 처리
    void removeBySender(int index);



}
