package com.project.sbarchive.security.handler;


import com.project.sbarchive.security.dto.MemberSecurityDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("============================================================");
        log.info("==================== CustomSocialLoginSuccessHandler AuthenticationSuccessHandler ====================");
        log.info(authentication.getPrincipal());

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String userId = null;
        String email = null;

        // 최초 회원가입 후 유지된 로그인 상태에서 userId가 이메일로 설정되어 있어서 구분필요
        if(userService.getUserId(memberSecurityDTO.getUserId()) == null){
            userId = memberSecurityDTO.getUserId();
            email = memberSecurityDTO.getEmail();
        } else {
            userId = userService.getUserId(memberSecurityDTO.getUserId());
            email = memberSecurityDTO.getUserId();
        }

        log.info("@@@@@@@@@@@@@@@ " + userId);

        //  소셜 로그인이고 회원의 패스워드가 1111 이라면
        if(memberSecurityDTO.getUserId().equals("dupl_error")) {
            log.info("ㅎㅇㅎㅇ 석세스 핸들러임");
            response.sendRedirect("/error/dupl");
        }
        else if(userService.isSocialPassword(email)) {
            log.info("==================== Should Change Password ====================");
            log.info("Redirect to Member Modify");

            response.sendRedirect("/my/modifySocialPasswd");

            return;
        } else {
            response.sendRedirect("/index");
        }
    }
}
