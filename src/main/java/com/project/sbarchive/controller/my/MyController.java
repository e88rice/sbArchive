package com.project.sbarchive.controller.my;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.service.board.BoardService;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public void myPageGET(Principal principal, Model model) {
        log.info("===== myPage Get Controller =====");
        log.info("==================== myPage Get Controller ====================");
        UserVO userVO = userService.getUserInfo(principal.getName());
        UserDTO userDTO = modelMapper.map(userVO, UserDTO.class);

        model.addAttribute("userInfo", userDTO);
    }

    @GetMapping("/auth")
    public void authGET(Principal principal, Model model) {
        log.info("==================== authForm Get Controller ====================");
        String username = null;
        if(principal != null) { // 로그인 상태라면
            username = principal.getName();
        };
        model.addAttribute("username", username);
    }

    @PostMapping("/auth")
    public String authPOST(String userId, String passwd, Model model) {
        log.info("==================== authForm POST Controller ====================");
        boolean isConfirmPassword = userService.isConfirmPassword(userId, passwd);

        log.info("isConfirmPassword : " + isConfirmPassword);

        if(isConfirmPassword) {
            return "redirect:/my/modifyMyInfo";
        } else {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
            model.addAttribute("username", userId);
            return "/my/auth";
        }
    }

    @GetMapping("/modifyMyInfo")
    public void modifyMyInfoGET() {
        log.info("==================== modifyMyInfo Get Controller ====================");
    }

    @PostMapping("/modifyNickname")
    public String modifyNickname(String userId, String email, String nickname) {
        log.info("==================== modifyNickname POST Controller ====================");
        userService.modifyNickname(userId, email, nickname);

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
}
