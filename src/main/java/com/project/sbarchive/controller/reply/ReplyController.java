package com.project.sbarchive.controller.reply;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.service.reply.ReplyService;
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

    @GetMapping(value="/add")
    public String addReplyGET() {
        log.info("/reply/boardId/addGET");
        return "/reply/add";
    }

    @PreAuthorize("hasRole('USER')") // Role이 유저인 유저만 접근 가능
    @ApiOperation(value="Reply add POST", notes="POST 방식으로 댓글 등록")
    @PostMapping(value="/add", consumes= MediaType.APPLICATION_JSON_VALUE) // consumes=데이터가 어떤 타입인지 명시
    public Map<String, Integer> addReplyPOST(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult, Principal principal) throws BindException { // RequestBody: JSON 문자열을 ReplyDTO로 변환

        log.info("replyDTO: "+replyDTO);
        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }

        Map<String, Integer> resultMap=new HashMap<>();

        replyDTO.setUserId(principal.getName());
        int replyId=replyService.addReply(replyDTO);
        resultMap.put("replyId", replyId);

        return resultMap;
    }

    @ApiOperation(value = "Replies of Board", notes="GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{boardId}")
    public PageResponseDTO<ReplyDTO> getReplyList(@PathVariable("boardId") int boardId, PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ReplyDTO> responseDTO=replyService.getReplyList(boardId, pageRequestDTO);
        log.info("여기는 댓글 리스트 조회하는 곳: "+responseDTO);
        return responseDTO;

    }

    @ApiOperation(value = "Read Reply", notes="GET 방식으로 특정 댓글 조회")
    @GetMapping(value = "/{replyId}")
    public ReplyDTO getReply(@PathVariable("replyId") int replyId, Model model) {
        ReplyDTO replyDTO=replyService.getReply(replyId);
        model.addAttribute("dto", replyDTO);
        log.info("replyDTO::: "+replyDTO);
        return replyDTO;
    }

    @ApiOperation(value = "Delete Reply", notes="DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{replyId}")
    public Map<String, Integer> removeReply(@PathVariable("replyId") int replyId) {
        replyService.removeReply(replyId);
        Map<String, Integer> resultMap=new HashMap<>();
        resultMap.put("replyId", replyId);
        return resultMap;
    }

    @ApiOperation(value = "Update Reply", notes="PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{replyId}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> modifyReply(@PathVariable("replyId") int replyId, @RequestBody ReplyDTO replyDTO) {
        // @RequestBody가 있어야 입력할 수 있는 게 나옴
        replyDTO.setReplyId(replyId); // 번호 일치시킴
        replyService.modifyReply(replyDTO);
        Map<String, Integer> resultMap=new HashMap<>();

        resultMap.put("replyId", replyId);

        return resultMap;
    }

}