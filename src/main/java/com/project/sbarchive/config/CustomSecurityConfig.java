package com.project.sbarchive.config;

import com.project.sbarchive.security.handler.Custom403Handler;
import com.project.sbarchive.security.handler.CustomSocialLoginSuccessHandler;
import com.project.sbarchive.security.handler.CustomTempLoginSuccessHandler;
import com.project.sbarchive.security.service.CustomUserDetailService;
import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 어노테이션으로 권한을 설정할 수 있게 하는 어노테이션
public class CustomSecurityConfig {
    private final UserService userService;
    private final DataSource dataSource;
    private final CustomUserDetailService customUserDetailService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("============= Spring Security Configure =============");


        // 커스텀 로그인 페이지
        httpSecurity.formLogin()
                .loginPage("/user/login") // 로그인을 진행할 페이지
                .successHandler(authenticationSuccessHandler()); // 임시 로그인인지 체크하기

        // CSRF 토큰 비활성화
        httpSecurity.csrf().disable();

        // 자동 로그인 설정
        httpSecurity.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailService)
                .tokenValiditySeconds(60 * 60 * 24 * 30);


        // 액세스가 거부 됐을 때 설정해둔 Custom403Handler 객체의 설정으로 처리
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); // 403

        // 소셜 로그인 + successHandler(자동 가입 후 정보수정으로 이동)
        httpSecurity.oauth2Login()
                .loginPage("/user/login")
                .successHandler(authenticationSuccessHandler2());


        return httpSecurity.build();
        // filterChain() 메소드가 동작하면 이전과 달리 /board/list에 바로 접근 가능.
        // '/login' 에는 접근이 안 됨.
    }

    // 보안에 문제가 있을 수 있는 설정
    // 기존 StrictHttpFirewall 은 특수 문자에 대한 엄격하게 검증 하는데
    // 이를 느슨하게 만들어 보안이 다소 약해질 수 있음
    // DefaultHttpFirewall 을 커스터마이징 해서 URL에 인코딩이 포함된 것도 허용하는 설정임.
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("============= Spring Security Customize =============");

        // 정적 파일 경로에 시큐리티 적용을 안함.
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 자동 로그인 쿠키와 관련된 데이터를 보관하는 테이블과 소통하는 객체 같음
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomTempLoginSuccessHandler(userService);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler2() {
        return new CustomSocialLoginSuccessHandler(userService);
    }

}
