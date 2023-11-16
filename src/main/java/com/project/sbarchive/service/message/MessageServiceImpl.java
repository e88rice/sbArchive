package com.project.sbarchive.service.message;

import com.project.sbarchive.dto.message.MessageDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.mapper.message.MessageMapper;
import com.project.sbarchive.vo.message.MessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ModelMapper modelMapper;
    private final MessageMapper messageMapper;

    @Override
    public void add(String senderId, String receiverId, String content) {
        messageMapper.add(senderId, receiverId, content);
    }

    @Override
    public PageResponseDTO<MessageDTO> getAllMsgByReceiver(String receiverId, PageRequestDTO pageRequestDTO) {
        // 받은 쪽지 리스트
        List<MessageDTO> dtoList = messageMapper.getAllMsgByReceiver(receiverId, pageRequestDTO.getSkip(), pageRequestDTO.getSize())
                .stream().map(messageVO -> modelMapper.map(messageVO, MessageDTO.class)).collect(Collectors.toList());


        // 받은 쪽지 총 개수
        int total = messageMapper.getAllMsgByReceiverCount(receiverId);

        return PageResponseDTO.<MessageDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getAllMsgByReceiverCount(String receiverId) {
        return messageMapper.getAllMsgByReceiverCount(receiverId);
    }

    @Override
    public PageResponseDTO<MessageDTO> getAllMsgBySender(String senderId, PageRequestDTO pageRequestDTO) {
        // 보낸 쪽지 리스트
        List<MessageDTO> dtoList = messageMapper.getAllMsgByReceiver(senderId, pageRequestDTO.getSkip(), pageRequestDTO.getSize())
                .stream().map(messageVO -> modelMapper.map(messageVO, MessageDTO.class)).collect(Collectors.toList());

        // 보낸 쪽지 총 개수
        int total = messageMapper.getAllMsgByReceiverCount(senderId);

        return PageResponseDTO.<MessageDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public int getAllMsgBySenderCount(String senderId) {
        return messageMapper.getAllMsgBySenderCount(senderId);
    }

    @Override
    public MessageDTO getMessage(int index) {
        return modelMapper.map(messageMapper.getMessage(index), MessageDTO.class);
    }

    @Override
    public void checkMessage(int index) {
        messageMapper.checkMessage(index);
    }

    @Override
    public void removeByReceiver(int index) {
        messageMapper.removeByReceiver(index);
    }

    @Override
    public void removeBySender(int index) {
        messageMapper.removeBySender(index);
    }
}
