package com.project.sbarchive.service.reply;

import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.mapper.reply.ReplyMapper;
import com.project.sbarchive.vo.reply.ReplyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ModelMapper modelMapper;
    private final ReplyMapper replyMapper;


    @Override
    public int add(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.add(replyVO);
        return replyVO.getReplyId();
    }

    @Override
    public ReplyDTO getReply(int replyId) {
        log.info("ReplyServiceImpl - replyId: "+replyId);
        ReplyVO replyVO=replyMapper.getReply(replyId);
        ReplyDTO replyDTO=modelMapper.map(replyVO, ReplyDTO.class);
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        return replyDTO;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.modify(replyVO);
    }

    @Override
    public void remove(int replyId) {
        log.info("ReplyServiceImpl - replyId: "+replyId);
        replyMapper.remove(replyId);
    }

    @Override
    public List<ReplyDTO> getReplyList(int boardId) {
        List<ReplyVO> voList=replyMapper.getReplyList(boardId);
        List<ReplyDTO> dtoList=new ArrayList<>();
        for(ReplyVO replyVO:voList) {
            dtoList.add(modelMapper.map(replyVO, ReplyDTO.class));
        }
//        int total=replyMapper.getReplyCount(boardId);

//        return PageResponseDTO.<ReplyDTO>withAll()
//                .dtoList(dtoList)
//                .total(total)
//                .pageRequestDTO(pageRequestDTO)
//                .build();
        return dtoList;
    }

    @Override
    public int getReplyCount(int boardId) {
        return replyMapper.getReplyCount(boardId);
    }
}
