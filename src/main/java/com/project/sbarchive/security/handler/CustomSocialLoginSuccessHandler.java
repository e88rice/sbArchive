package com.project.sbarchive.security.handler;


import com.project.sbarchive.security.dto.MemberSecurityDTO;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;

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

        if(userService.getUserId(memberSecurityDTO.getUserId()) == null){
            userId = memberSecurityDTO.getUserId();
        } else {
            userId = userService.getUserId(memberSecurityDTO.getUserId());
        }
        String encodePw = memberSecurityDTO.getPasswd();
        log.info("@@@@@@@@@@@@@@@ " + userId);
        log.info("@@@@@@@@@@@@@@@ " + encodePw);

        // 소셜 로그인이고 회원의 패스워드가 1111 이라면
        if(userService.isSocialPassword(userId) == 1) {
            log.info("==================== Should Change Password ====================");
            log.info("Redirect to Member Modify");

            response.sendRedirect("/my/modifySocialPasswd");

            return;
        } else {
            response.sendRedirect("/index");
        }
    }
}
