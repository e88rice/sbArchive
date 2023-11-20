package com.project.sbarchive.mapper.user;

import com.project.sbarchive.vo.board.BoardVO;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import com.project.sbarchive.vo.user.UserVO;
import com.project.sbarchive.vo.reply.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 회원 가입
    void registerUser(UserVO userVO);
    void addUserRole(String userId, Integer role_set);
    void addLog(String userId, String passwd);

    // 이메일 유효성 검사
    int emailCheck(String email);

    // 아이디 유효성 검사
    int userIdCheck(String userId);

    // 로그인
    UserVO getUserInfo(String userId);

    // 아이디 찾기
    String getUserId(String email);

    // 비밀번호 확인
    String userPasswdCheck(String userId);

    // 비밀번호 찾기 (아이디 이메일 일치 확인)
    int accountCheck(String userId, String email);

    // 비밀번호 재설정
    void updatePassword(String userId, String passwd);
    void updateLogTemp(String userId, String passwd);
    void updateLog(String userId, String passwd);

    int isTempPassword(String userId);

    // 닉네임 재설정
    void modifyNickname(String userId, String email, String nickname);

    // 내가 쓴 글 목록 출력
    List<BoardVO> getMyBoardList(String userId, int skip, int size, String[] types, String keyword);
    int getMyBoardCount(String userId, String[] types, String keyword);

    // 내가 쓴 아카이브 목록 출력
    List<SignBoardVO> getMySignboardList(String userId, int skip, int size, String[] types, String keyword);
    int getMySignboardCount(String userId, String[] types, String keyword);

    // 내가 쓴 댓글 목록 출력
    List<ReplyVO> getMyReplyList(String userId, int skip, int size, String[] types, String keyword);
    int getMyReplyCount(String userId, String[] types, String keyword);
}
