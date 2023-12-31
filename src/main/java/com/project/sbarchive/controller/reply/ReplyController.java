package com.project.sbarchive.controller.reply;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController // 결과값을 json으로 준다
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;

    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @ApiOperation(value="Reply add POST", notes="댓글 등록")
    @PostMapping(value="/add", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> addReplyPOST(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult, Principal principal) throws BindException {

        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }

        Map<String, Integer> resultMap=new HashMap<>();

        replyDTO.setUserId(principal.getName());
        int replyId=replyService.addReply(replyDTO);
        replyDTO.setReplyId(replyId);
        replyService.upReplyCount(replyDTO.getBoardId()); // 게시판 리플카운트 +1 DB = board
        resultMap.put("replyId", replyId);

        String userId = principal.getName();
        userService.lvPointUp(userId);
        UserVO userVO = userService.getUserInfo(userId);
        userService.checkLevelUp(userId, userVO.getLevel(), userVO.getLvPoint());

        return resultMap;
    }

    @ApiOperation(value = "Update Reply", notes="PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/replyId/{replyId}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> modifyReplyId(@PathVariable("replyId") int replyId, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setReplyId(replyId);
        replyService.modifyReplyId(replyDTO);
        Map<String, Integer> resultMap=new HashMap<>();

        resultMap.put("replyId", replyId);

        return resultMap;
    }


    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @ApiOperation(value="ReReply add POST", notes="POST 방식으로 댓글 등록")
    @PostMapping(value="/addRe", consumes= MediaType.APPLICATION_JSON_VALUE) // consumes=데이터가 어떤 타입인지 명시
    public Map<String, Integer> addReReplyPOST(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult, Principal principal) throws BindException { // RequestBody: JSON 문자열을 ReplyDTO로 변환

        log.info("replyDTO: "+replyDTO);
        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }

        Map<String, Integer> resultMap=new HashMap<>();

        replyDTO.setUserId(principal.getName());
        int replyId=replyService.addReReply(replyDTO);

        replyService.upReplyCount(replyDTO.getBoardId()); // 게시판 리플카운트 +1 DB = board

        resultMap.put("replyId", replyId);

        String userId = principal.getName();
        userService.lvPointUp(userId);
        UserVO userVO = userService.getUserInfo(userId);
        userService.checkLevelUp(userId, userVO.getLevel(), userVO.getLvPoint());

        return resultMap;
    }

    @ApiOperation(value = "Replies of Board", notes="특정 게시물의 원댓글 목록")
    @GetMapping(value = "/list/{boardId}/{replyDepth}")
    public PageResponseDTO<ReplyDTO> getReplyList(@PathVariable("boardId") int boardId,
                                                  @PathVariable("replyDepth") boolean replyDepth,
                                                  PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ReplyDTO> responseDTO=replyService.getReplyList(boardId, replyDepth, pageRequestDTO);
        return responseDTO;

    }

    @ApiOperation(value = "ReReplies of Board", notes="GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/re/{boardId}/{parentReplyId}/{replyDepth}")
    public List<ReplyDTO> getReReplies(@PathVariable("boardId") int boardId,
                                       @PathVariable("parentReplyId") int parentReplyId,
                                       @PathVariable("replyDepth") boolean replyDepth) {

        List<ReplyDTO> replyDTO=replyService.getReReplies(boardId, parentReplyId, replyDepth);
        return replyDTO;
    }

    @ApiOperation(value = "Read Reply", notes="특정 댓글 조회")
    @GetMapping(value = "/{replyId}")
    public ReplyDTO getReply(@PathVariable("replyId") int replyId, Model model) {
        ReplyDTO replyDTO=replyService.getReply(replyId);
        model.addAttribute("dto", replyDTO);
        return replyDTO;
    }

    @ApiOperation(value = "Update Reply", notes="특정 댓글/대댓글 수정")
    @PutMapping(value = "/{replyId}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> modifyReply(@PathVariable("replyId") int replyId, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setReplyId(replyId);
        replyService.modifyReply(replyDTO);
        Map<String, Integer> resultMap=new HashMap<>();

        resultMap.put("replyId", replyId);

        return resultMap;
    }

    @ApiOperation(value = "Delete ReReply", notes="대댓글 삭제")
    @DeleteMapping(value = "/{replyId}")
    public Map<String, Integer> removeReReply(@PathVariable("replyId") int replyId) {
        ReplyDTO replyDTO = replyService.getReply(replyId);
        replyService.downReplyCount(replyDTO.getBoardId());
        replyService.removeReReply(replyId);

        Map<String, Integer> resultMap=new HashMap<>();
        resultMap.put("replyId", replyId);
        return resultMap;
    }

    @ApiOperation(value = "Update Reply", notes="댓글 논리적 삭제")
    @PutMapping(value = "/re/{replyId}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> removeReply(@PathVariable("replyId") int replyId, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setReplyId(replyId);
        replyDTO = replyService.getReply(replyId);
        log.info(replyDTO.getBoardId());
        replyService.downReplyCount(replyDTO.getBoardId());
        replyService.removeReply(replyDTO);
        Map<String, Integer> resultMap=new HashMap<>();

        resultMap.put("replyId", replyId);

        return resultMap;
    }


}