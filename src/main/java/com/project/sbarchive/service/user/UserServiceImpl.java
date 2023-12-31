package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.board.BoardLikeDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.mapper.user.UserMapper;
import com.project.sbarchive.vo.user.UserRole;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /* 회원 가입 */
    @Override
    public void registerUser(UserDTO userDTO) {
        log.info("============= registerUser Service =============");
        UserVO userVO = modelMapper.map(userDTO, UserVO.class);
        userVO.changeMpw(passwordEncoder.encode(userVO.getPasswd()));
        userVO.addRole(UserRole.USER);

        userMapper.registerUser(userVO);
        List<Integer> role_set = new ArrayList<>();
        for(int i = 0; i < userVO.getRoleSet().size(); i++ ){
            role_set.add(i);
        }
        addUserRole(userVO.getUserId(), role_set);
        addLog(userVO.getUserId(), userVO.getPasswd(), userVO.getEmail());
    }

    @Override
    public void addUserRole(String userId, List<Integer> role_set) {
        log.info("============= addUserRole Service =============");
        for(int i=0; i<role_set.size(); i++) {
            userMapper.addUserRole(userId, role_set.get(i));
        }
    }

    @Override
    public void addLog(String userId, String passwd, String email) {
        log.info("============= addUserRole Service =============");
        userMapper.addLog(userId, passwd, email);
    }

    @Override
    public int emailCheck(String email) {
        log.info("============= emailCheck Service =============");
        int cnt = userMapper.emailCheck(email);
        log.info("cnt : " + cnt);
        return cnt;
    }

    @Override
    public int userIdCheck(String userId) {
        log.info("============= userIdCheck Service =============");
        int cnt = userMapper.userIdCheck(userId);
        log.info("cnt : " + cnt);
        return cnt;
    }


    @Override
    public UserVO getUserInfo(String userId) {
        log.info("============= getUserInfo Service =============");
        return userMapper.getUserInfo(userId);
    }

    @Override
    public String getUserId(String email) {
        return userMapper.getUserId(email);
    }

    @Override
    public boolean isConfirmPassword(String userId, String passwd) {
        log.info("============= isConfirmPassword Service =============");

        String dbPassword = userMapper.userPasswdCheck(userId);
        boolean result = passwordEncoder.matches(passwd, dbPassword);

        log.info("@@@@@@@@@@@@@@@@@@@@ " + result + " @@@@@@@@@@@@@@@@@@@@");

        return result;
    }



    @Override
    public void updateTempPassword(String userId, String passwd) {
        log.info("============= updateTempPassword Service =============");

        String pw = passwordEncoder.encode(passwd);
        userMapper.updatePassword(userId, pw);
        updateLogTemp(userId, pw);
    }

    @Override
    public void updateLogTemp(String userId, String passwd) {
        userMapper.updateLogTemp(userId, passwd);
    }

    @Override
    public boolean isTempPassword(String email) {
        return userMapper.isTempPassword(email);
    }

    @Override
    public void updatePassword(String userId, String passwd) {
        log.info("============= updatePassword Service =============");

        String pw = passwordEncoder.encode(passwd);
        userMapper.updatePassword(userId, pw);
        updateLog(userId, pw);

    }

    @Override
    public void updateLog(String userId, String passwd) {
        userMapper.updateLog(userId, passwd);
    }

    @Override
    public void modifyNickname(String userId, String email, String nickname) {
        log.info("============= modifyNickname Service =============");
        userMapper.modifyNickname(userId, email, nickname);
    }

    @Override
    public void modifyEmail(String userId, String email) {
        log.info("============= modifyEmail Service =============");
        userMapper.modifyEmail(userId, email);
    }

    @Override
    public PageResponseDTO<BoardDTO> getMyBoardList(String userId, PageRequestDTO pageRequestDTO) {
        log.info("============= getMyBoardList Service =============");

        List<BoardDTO> dtoList = userMapper.getMyBoardList(userId, pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                        pageRequestDTO.getTypes(), pageRequestDTO.getKeyword())
                .stream().map(boardVO -> modelMapper.map(boardVO, BoardDTO.class)).collect(Collectors.toList());

        log.info(dtoList);

        int total = userMapper.getMyBoardCount(userId, pageRequestDTO.getTypes(), pageRequestDTO.getKeyword());

        log.info("total : " + total);

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<SignBoardDTO> getMySignBoardList(String userId, PageRequestDTO pageRequestDTO) {
        log.info("============= getMyBoardList Service =============");

        List<SignBoardDTO> dtoList = userMapper.getMySignboardList(userId, pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                        pageRequestDTO.getTypes(), pageRequestDTO.getKeyword())
                .stream().map(signboardVO -> modelMapper.map(signboardVO, SignBoardDTO.class)).collect(Collectors.toList());

        log.info(dtoList);

        int total = userMapper.getMySignboardCount(userId, pageRequestDTO.getTypes(), pageRequestDTO.getKeyword());

        log.info("total : " + total);

        PageResponseDTO<SignBoardDTO> pageResponseDTO = PageResponseDTO.<SignBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<ReplyDTO> getMyReplyList(String userId, PageRequestDTO pageRequestDTO) {
        log.info("============= getMyReplyList Service =============");

        List<ReplyDTO> dtoList = userMapper.getMyReplyList(userId, pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                        pageRequestDTO.getTypes(), pageRequestDTO.getKeyword())
                .stream().map(replyVO -> modelMapper.map(replyVO, ReplyDTO.class)).collect(Collectors.toList());

        log.info(dtoList);

        int total = userMapper.getMyReplyCount(userId, pageRequestDTO.getTypes(), pageRequestDTO.getKeyword());

        log.info("total : " + total);

        PageResponseDTO<ReplyDTO> pageResponseDTO = PageResponseDTO.<ReplyDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<BoardLikeDTO> getMyLikedList(String userId, PageRequestDTO pageRequestDTO) {
        log.info("============= getMyLikedList Service =============");

        List<BoardLikeDTO> dtoList = userMapper.getMyLikedList(userId, pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                        pageRequestDTO.getTypes(), pageRequestDTO.getKeyword())
                .stream().map(boardLikeVO -> modelMapper.map(boardLikeVO, BoardLikeDTO.class)).collect(Collectors.toList());

        log.info(dtoList);

        int total = userMapper.getMyLikedCount(userId, pageRequestDTO.getTypes(), pageRequestDTO.getKeyword());

        log.info("total : " + total);

        PageResponseDTO<BoardLikeDTO> pageResponseDTO = PageResponseDTO.<BoardLikeDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public void socialRegister(UserDTO userDTO) {
        registerUser(userDTO);
        userMapper.updateLogSocial(userDTO.getUserId(), userDTO.getPasswd());
    }

    @Override
    public boolean isSocialPassword(String email) {
        return userMapper.isSocialPassword(email);
    }

    @Override
    public void lvPointUp(String userId) {
        userMapper.lvPointUp(userId);
    }

    @Override
    public void checkLevelUp(String userId, int level, int lvPoint) {
        userMapper.checkLevelUp(userId, level, lvPoint);
    }

    @Override
    public int checkDupl(String email) {
        return userMapper.checkDupl(email);
    }

    @Override
    public UserDTO getUserInfoByEmail(String email) {
        UserDTO userDTO = modelMapper.map(userMapper.getUserInfoByEmail(email), UserDTO.class);
        return userDTO;
    }

    @Override
    public boolean withdrawal(String userId, String passwd) {
        log.info("============= withdrawal Service =============");

        String dbPassword = userMapper.userPasswdCheck(userId);

        if(passwordEncoder.matches(passwd, dbPassword)) {
            userMapper.withdrawal(userId, passwd);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int accountCheck(String userId, String email) {
        log.info("============= accountCheck Service =============");
        int cnt = userMapper.accountCheck(userId, email);
        log.info("cnt : " + cnt);
        return cnt;
    }


}
