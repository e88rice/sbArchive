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
public class CustomTempLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("============================================================");
        log.info("==================== CustomSocialLoginSuccessHandler AuthenticationSuccessHandler ====================");
        log.info(authentication.getPrincipal());

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String email = memberSecurityDTO.getEmail();

        // 임시 비밀번호라면
        if(userService.isTempPassword(email)) {
            log.info("==================== Should Change Password ====================");
            log.info("Redirect to Member Modify");

            response.sendRedirect("/my/modifyPasswd?temp");

            return;
        } else {
            response.sendRedirect("/index");
        }

    }
}
