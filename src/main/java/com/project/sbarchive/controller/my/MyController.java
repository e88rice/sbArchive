package com.project.sbarchive.controller.my;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.reply.ReplyService;
import com.project.sbarchive.service.signboard.SignBoardService;
import com.project.sbarchive.security.dto.MemberSecurityDTO;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;

@Log4j2
@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final UserService userService;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final SignBoardService signBoardService;
    private final ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public void myPageGET(Principal principal, Model model) {
        log.info("===== myPage Get Controller =====");
        log.info("==================== myPage Get Controller ====================");
        UserVO userVO = userService.getUserInfo(principal.getName());
        UserDTO userDTO = modelMapper.map(userVO, UserDTO.class);
        String userId = principal.getName();
        userService.lvPointUp(userId);
        userVO = userService.getUserInfo(userId);
        userService.checkLevelUp(userId, userVO.getLevel(), userVO.getLvPoint());
        log.info(userVO);

        model.addAttribute("userInfo", userDTO);
    }

    @GetMapping("/modifyMyInfoAuth")
    public void modifyMyInfoAuthGET(Principal principal, Model model) {
        log.info("==================== authForm Get Controller ====================");
        String username = null;
        if(principal != null) { // 로그인 상태라면
            username = principal.getName();
        };
        model.addAttribute("username", username);
    }

    @PostMapping("/modifyMyInfoAuth")
    public String modifyMyInfoAuthPOST(String userId, String passwd, Model model) {
        log.info("==================== authForm POST Controller ====================");
        boolean isConfirmPassword = userService.isConfirmPassword(userId, passwd);

        log.info("isConfirmPassword : " + isConfirmPassword);

        if(isConfirmPassword) {
            return "redirect:/my/modifyMyInfo";
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
            model.addAttribute("username", userId);
            return "/my/modifyMyInfoAuth";
        }
    }

    @GetMapping("/modifyMyInfo")
    public void modifyMyInfoGET() {
        log.info("==================== modifyMyInfo Get Controller ====================");
    }

    @PostMapping("/modifyMyInfo")
    public String modifyNickname(String userId, String email, String nickname,
                                 Authentication authentication, Model model) {
        log.info("==================== modifyMyInfo POST Controller ====================");
        userService.modifyNickname(userId, email, nickname);
        // MemberSecurityDTO는 참조 변수라 현재 로그인한 UserDetails 객체랑 동기화 되어있음
        // 그래서 Setter를 이용해 값을 변경하면 세션에 있는 UserDetails 값도 변경이 됨.
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        memberSecurityDTO.setNickname(nickname);

        model.addAttribute("msg", "회원 정보가 성공적으로 수정되었습니다.");
        return "redirect:/my/mypage";
    }

    @GetMapping("/modifyEmailAuth")
    public void modifyEmailAuthGET(Principal principal, Model model) {
        log.info("==================== modifyEmailAuth Get Controller ====================");
        String username = null;
        if(principal != null) { // 로그인 상태라면
            username = principal.getName();
        };
        model.addAttribute("username", username);
    }

    @PostMapping("/modifyEmailAuth")
    public String modifyEmailAuthPOST(String userId, String passwd, Model model) {
        log.info("==================== modifyEmailAuth POST Controller ====================");
        boolean isConfirmPassword = userService.isConfirmPassword(userId, passwd);

        log.info("isConfirmPassword : " + isConfirmPassword);

        if(isConfirmPassword) {
            return "redirect:/my/modifyEmail";
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
            model.addAttribute("username", userId);
            return "/my/modifyEmailAuth";
        }
    }

    @GetMapping("/modifyEmail")
    public void modifyEmailGET() {
        log.info("==================== modifyEmail Get Controller ====================");
    }

    @PostMapping("/modifyEmail")
    public String modifyEmail(Principal principal, String email,
                              Authentication authentication, Model model) {
        log.info("==================== modifyEmail POST Controller ====================");
        userService.modifyEmail(principal.getName(), email);
        // MemberSecurityDTO는 참조 변수라 현재 로그인한 UserDetails 객체랑 동기화 되어있음
        // 그래서 Setter를 이용해 값을 변경하면 세션에 있는 UserDetails 값도 변경이 됨.
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        memberSecurityDTO.setEmail(email);

        model.addAttribute("msg", "회원 정보가 성공적으로 수정되었습니다.");
        return "redirect:/my/mypage";
    }

    @GetMapping("/modifyPasswd")
    public void modifyPasswdGET() {
        log.info("==================== modifyPasswd Get Controller ====================");
    }

    @PostMapping("/modifyPasswd")
    public String modifyPasswdPOST(Principal principal, String passwd, String existPasswd, Model model) {
        log.info("==================== modifyPasswd POST Controller ====================");
        if(userService.isConfirmPassword(principal.getName(), existPasswd)){
            userService.updatePassword(principal.getName(), passwd);
            return "redirect:/index";
        } else {
            model.addAttribute("msg", "기존 비밀번호가 일치하지 않습니다");
            return "/my/modifyPasswd";
        }
    }

    @GetMapping("/existPasswdChecked")
    @ResponseBody
    public boolean existPasswdChecked(Principal principal, String passwd){
        log.info("==================== existPasswdChecked GET Controller ====================");
        return userService.isConfirmPassword(principal.getName(), passwd);
    }

    @GetMapping("/mySignBoardList")
    public void mySignBoardList(Principal principal, Model model, @Valid PageRequestDTO pageRequestDTO,
                                BindingResult bindingResult) {
        log.info("==================== mySignBoardList Get Controller ====================");
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        PageResponseDTO<SignBoardDTO> boardDTOPageResponseDTO = userService.getMySignBoardList(principal.getName(), pageRequestDTO);
        model.addAttribute("responseDTO", boardDTOPageResponseDTO);
    }

    @GetMapping("/myBoardList")
    public void myBoardList(Principal principal, Model model, @Valid PageRequestDTO pageRequestDTO,
                            BindingResult bindingResult) {
        log.info("==================== myBoardList Get Controller ====================");
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO = userService.getMyBoardList(principal.getName(), pageRequestDTO);
        model.addAttribute("responseDTO",boardDTOPageResponseDTO );
    }

    @GetMapping("/myReplyList")
    public void myReplyList(Principal principal, Model model, @Valid PageRequestDTO pageRequestDTO,
                            BindingResult bindingResult) {
        log.info("==================== myReplyList Get Controller ====================");
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        PageResponseDTO<ReplyDTO> boardDTOPageResponseDTO = userService.getMyReplyList(principal.getName(), pageRequestDTO);
        model.addAttribute("responseDTO",boardDTOPageResponseDTO );
    }

    @PostMapping("/deleteSelectedBoard")
    public String deleteSelectedBoard(int[] boardId) {
        log.info("==================== deleteSelectedBoard POST Controller ====================");
        for(int id : boardId) {
            log.info(id);
            boardService.remove(id);
        }
        return "redirect:/my/myBoardList";
    }

    @PostMapping("/deleteSelectedSignBoard")
    public String deleteSelectedSignBoard(int[] signboardId) {
        log.info("==================== deleteSelectedSignBoard POST Controller ====================");
        for(int id : signboardId) {
            log.info(id);
            signBoardService.removeSignboard(id);
        }
        return "redirect:/my/mySignBoardList";
    }

    @PostMapping("/deleteSelectedReply")
    public String deleteSelectedReply(int[] replyId) {
        log.info("==================== deleteSelectedReply POST Controller ====================");
        for(int id : replyId) {
            log.info(id);
            ReplyDTO replyDTO = replyService.getReply(id);
            replyService.removeReply(replyDTO);
            if(replyDTO.isReplyDepth()){
                // 대댓글 삭제
                replyService.removeReReply(id);
            } else {
                // 댓글 삭제
                replyService.removeReply(replyDTO);
            }
        }
        return "redirect:/my/myReplyList";
    }

    @GetMapping("/withdrawal")
    public void withdrawalGET() {
        log.info("==================== withdrawal GET Controller ====================");
    }

    @PostMapping("/withdrawal")
    public String withdrawal(Authentication authentication, String passwd, Model model){
        log.info("==================== withdrawal POST Controller ====================");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean result = userService.withdrawal(userDetails.getUsername(), passwd);

        if (result) {
            return "redirect:/logout";
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
            return "/my/withdrawal";
        }
    }
}
