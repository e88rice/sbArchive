package com.project.sbarchive.service.user;

import com.project.sbarchive.dto.board.BoardDTO;
import com.project.sbarchive.dto.board.BoardLikeDTO;
import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.reply.ReplyDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.vo.user.UserVO;

import java.util.List;

public interface UserService {
    // 회원 가입
    void registerUser(UserDTO userDTO);
    void addUserRole(String userId, List<Integer> role_set);
    void addLog(String userId, String passwd, String email);

    // 이메일 유효성 검사
    int emailCheck(String email);

    // 아이디 유효성 검사
    int userIdCheck(String userId);

    // 로그인
    UserVO getUserInfo (String userId);

    // 아이디 찾기
    String getUserId (String email);

    // 비밀번호 확인
    boolean isConfirmPassword (String userId, String passwd);

    // 비밀번호 찾기 (아이디 이메일 일치 확인)
    int accountCheck(String userId, String email);

    // 임시 비밀번호 발급
    void updateTempPassword (String userId, String passwd);
    // 비밀번호 임시 재설정
    void updateLogTemp (String userId, String passwd);

    // 임시 비밀번호인지 확인
    int isTempPassword(String userId);
    // 비밀번호 재설정
    void updatePassword (String userId, String passwd);
    void updateLog (String userId, String passwd);

    // 닉네임 재설정
    void modifyNickname(String userId, String email, String nickname);

    // 이메일 재설정
    void modifyEmail(String userId, String email);

    // 내가 쓴 글 목록 출력
    PageResponseDTO<BoardDTO> getMyBoardList(String userId, PageRequestDTO pageRequestDTO);

    // 내가 쓴 아카이브 목록 출력
    PageResponseDTO<SignBoardDTO> getMySignBoardList(String userId, PageRequestDTO pageRequestDTO);

    // 내가 쓴 댓글 목록 출력
    PageResponseDTO<ReplyDTO> getMyReplyList(String userId, PageRequestDTO pageRequestDTO);

    // 내가 스크랩한 목록 출력
    PageResponseDTO<BoardLikeDTO> getMyLikedList(String userId, PageRequestDTO pageRequestDTO);

    // 소셜 회원가입
    void socialRegister(UserDTO userDTO);
    int isSocialPassword(String email);

    // 회원 탈퇴
    boolean withdrawal(String userId, String passwd);


    // 게시글 작성, 댓글 작성, 신고 활동 시 lvPoint + 10
    void lvPointUp(String userId);

    // lvPoint 업데이트 할 때 마다 lvPoint를 검사해서 조건을 만족할 시 레벨업을 시켜줌
    void checkLevelUp(String userId, int level, int lvPoint);


}
