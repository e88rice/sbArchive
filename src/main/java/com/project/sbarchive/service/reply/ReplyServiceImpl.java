package com.project.sbarchive.service.reply;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
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
    public int addReply(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.addReply(replyVO);
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
    public void modifyReply(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.modifyReply(replyVO);
    }

    @Override
    public void removeReply(int replyId) {
        log.info("ReplyServiceImpl - replyId: "+replyId);
        replyMapper.removeReply(replyId);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getReplyList(int boardId, PageRequestDTO pageRequestDTO) {

        List<ReplyVO> voList=replyMapper.getReplyList(boardId, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<ReplyDTO> dtoList=new ArrayList<>();
        for(ReplyVO replyVO:voList) {
            dtoList.add(modelMapper.map(replyVO, ReplyDTO.class));
        }
        int total=replyMapper.getReplyCount(boardId);

        return PageResponseDTO.<ReplyDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public int getReplyCount(int boardId) {
        return replyMapper.getReplyCount(boardId);
    }

    @Override
    public void upReplyCount(int boardId) {
        replyMapper.upReplyCount(boardId);
    }

    @Override
    public void downReplyCount(int boardId) {
        replyMapper.downReplyCount(boardId);
    }
}
