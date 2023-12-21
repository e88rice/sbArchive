package com.project.sbarchive.config;

import com.project.sbarchive.service.staticResource.StaticResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${com.project.sbarchive.upload.signboard.path}")
    private String signboardPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler : 스프링부트에서 확인할 폴더 위치 설정 ( img 폴더 밑에 아무거나 다 들어갈 수 있음 )
        // addResourceLocations : 실제 시스템의 폴더 위치, 윈도우 시스템의 경우 'file:///경로' 형태로 사용

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/",
                        "file:/var/app/current/application.jar!/BOOT-INF/classes!/static/images/",
                        "file:/var/app/current/upload/",
                        "file:./upload/");


        registry.addResourceHandler("layout/**").addResourceLocations("classpath:/templates/layout");

    }
}