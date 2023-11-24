package com.project.sbarchive.security.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.security.dto.MemberSecurityDTO;
import com.project.sbarchive.security.handler.Custom403Handler;
import com.project.sbarchive.service.user.UserService;
import com.project.sbarchive.vo.user.UserRole;
import com.project.sbarchive.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("=============== userRequest ===============");
        log.info(userRequest);

        log.info("=============== OAuth2User ===============");
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("@@@ NAME : " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;
        String userId = null;
        String nickname = null;
        switch (clientName) {
            case "kakao" :
                // 카카오 API로부터 반환된 "kakao_account" 속성을 맵으로 추출합니다.
                Map<String, Object> kakaoAccount = (Map<String, Object>) paramMap.get("kakao_account");
                email = (String) kakaoAccount.get("email");  // "kakao_account"에서 "email" 속성을 가져옵니다.
                userId = "k_"+paramMap.get("id");
                // "kakao_account"의 "profile" 속성을 맵으로 추출합니다.
                Map<String, Object> profile1 = (Map<String, Object>) kakaoAccount.get("profile");
                nickname = (String) profile1.get("nickname");
                break;

            case "Google" :
                email = "" + paramMap.get("email");
                userId = "k_"+paramMap.get("sub");
                nickname = (String) paramMap.get("name");
                break;
        }


        log.info("@@@ paramMap : " + paramMap);
        log.info("@@@ oAuth2User : " + oAuth2User);



        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info(email);
        log.info(userId);
        log.info(nickname);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        return generateDTO(email, userId, nickname, paramMap);
    }


    private MemberSecurityDTO generateDTO(String email, String userId, String nickname, Map<String, Object> paramMap) {
        log.info("=============== generateDTO ===============");

        // email 데이터베이스에 있는지 없는지 판단
        int result = userService.emailCheck(email);

        // email 이 데이터베이스에 없다면
        if(result == 0){
            log.info("@@@@@@@@@@@@ social member join @@@@@@@@@@@@");
            // 회원 추가
            UserVO userVO = UserVO.builder()
                    .userId(userId)
                    .passwd("1111")
                    .nickname(nickname)
                    .email(email)
                    .social(true)
                    .build();
            userVO.addRole(UserRole.USER);
            UserDTO userDTO = modelMapper.map(userVO, UserDTO.class);
            userService.socialRegister(userDTO);

            // MemberSecurityDTO 구성 및 반환
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(email, "1111", nickname, email, 0, 0,
                            "icon", now(), false, true,
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberSecurityDTO.setProps(paramMap);

            return memberSecurityDTO;
        }
        else {
            log.info("@@@@@@@@@@@@ social login @@@@@@@@@@@@");
            UserVO userVO = userService.getUserInfo(userId);
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(
                            userVO.getUserId(),
                            userVO.getPasswd(),
                            userVO.getNickname(),
                            userVO.getEmail(),
                            userVO.getLevel(),
                            userVO.getLvPoint(),
                            userVO.getIconName(),
                            userVO.getRegDate(),
                            userVO.isDel(),
                            userVO.isSocial(),
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
            return memberSecurityDTO;
        }
    }
}
