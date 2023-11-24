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
        log.info("ReplyServiceImpl - addReply replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.addReply(replyVO);
        return replyVO.getReplyId();
    }

    @Override
    public ReplyDTO getReply(int replyId) {
        log.info("ReplyServiceImpl - getReply replyId: "+replyId);
        ReplyVO replyVO=replyMapper.getReply(replyId);
        ReplyDTO replyDTO=modelMapper.map(replyVO, ReplyDTO.class);
        log.info("ReplyServiceImpl - replyDTO: "+replyDTO);
        return replyDTO;
    }

    @Override
    public void modifyReply(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - modifyReply replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.modifyReply(replyVO);
    }

    @Override
    public void modifyReplyId(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - modifyReplyId replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.modifyReplyId(replyVO);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getReplyList(int boardId, boolean replyDepth, PageRequestDTO pageRequestDTO) {
        log.info("ReplyServiceImpl - getReplyList boardId: " + boardId);

        // 댓글 목록을 가져올 때 대댓글 개수를 함께 가져오도록 수정
        List<ReplyVO> voList = replyMapper.getReplyList(boardId, replyDepth, pageRequestDTO.getSkip(), pageRequestDTO.getSize());

        List<ReplyDTO> dtoList = new ArrayList<>();
        for (ReplyVO replyVO : voList) {
            // 대댓글 수 조회
            int reReplyCount = replyMapper.countReReplies(replyVO.getReplyId());

            // ReplyDTO에 대댓글 수를 추가하고 세팅
            ReplyDTO replyDTO = modelMapper.map(replyVO, ReplyDTO.class);
            replyDTO.setReReplyCount(reReplyCount);

            // DTO로 변환하여 리스트에 추가
            dtoList.add(replyDTO);
        }

        int total = replyMapper.getReplyCount(boardId);

        log.info("dtoList: " + dtoList);
        log.info("pageRequestDTO: " + pageRequestDTO);

        return PageResponseDTO.<ReplyDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public int getReplyCount(int boardId) {
        log.info("ReplyServiceImpl - getReplyCount boardId: "+boardId);
        return replyMapper.getReplyCount(boardId);
    }


    // 여기서부터 대댓글 관련
    @Override
    public void removeReply(ReplyDTO replyDTO) { // 원 댓글 논리 삭제
        log.info("ReplyServiceImpl - removeReply(원 댓글 논리 삭제) replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.removeReply(replyVO);
    }

    @Override
    public void removeReReply(int replyId) { // 대댓글 삭제
        log.info("ReplyServiceImpl - removeReReply(대댓글 실제 삭제) replyId: "+replyId);
        replyMapper.removeReReply(replyId);
    }

    @Override
    public int addReReply(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - addReReply(대댓글 추가) replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.addReReply(replyVO);
        return replyVO.getReplyId();
    }

    @Override
    public List<ReplyDTO> getReReplies(int boardId, int parentReplyId, boolean replyDepth) {

        List<ReplyVO> voList=replyMapper.getReReplies(boardId, parentReplyId, replyDepth);
        List<ReplyDTO> dtoList=new ArrayList<>();
        for(ReplyVO replyVO:voList) {

            dtoList.add(modelMapper.map(replyVO, ReplyDTO.class));
        }
        log.info("dtoList: "+dtoList);

        return dtoList;
    }

    @Override
    public int countReReplies(int parentReplyId) {
        return replyMapper.countReReplies(parentReplyId);
    }

    // 여기까지 대댓글 관련


    // 신고 처리되었을 때, '관리자에 의해 규제된 댓글입니다' 문구 출력되게
    @Override
    public void reportedReply(ReplyDTO replyDTO) {
        log.info("ReplyServiceImpl - reportedReply replyDTO: "+replyDTO);
        ReplyVO replyVO=modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.reportedReply(replyVO);
    }






    // 여기서부터 Board에서 replyCount 나타낼 때 사용하는 메서드

    @Override
    public void upReplyCount(int boardId) {
        replyMapper.upReplyCount(boardId);
    }

    @Override
    public void downReplyCount(int boardId) {
        replyMapper.downReplyCount(boardId);
    }


}
