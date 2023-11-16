package com.project.sbarchive.mapper.message;

import com.project.sbarchive.vo.message.MessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    // 쪽지 보내기
    void add(String senderId, String receiverId, String content);

    // 받은 쪽지함
    List<MessageVO> getAllMsgByReceiver(String receiverId);

    // 보낸 쪽지함
    List<MessageVO> getAllMsgBySender(String senderId);

    // 쪽지 읽기
    MessageVO getMessage(int index);

    // 받은 쪽지함에서 쪽지 읽기를 했다면 읽음 처리 및 readDate 기록
    void checkMessage(int index);

    // 보낸 쪽지함에서 삭제 처리
    void removeBySender(String senderId);

    // 받은 쪽지함에서 삭제 처리
    void removeByReceiver(String receiverId);


}
