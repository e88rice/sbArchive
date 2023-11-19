package com.project.sbarchive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Swagger UI를 적용하면 정적 파일의 경로가 달라지는데
// 이를 EnableWebMvc 어노테이션을 이용해 WebMvcConfigurer 인터페이스를 구현하도록 하고
// addResourceHandlers를 재정의해 수정
@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer { // Swagger를 이용한다면 꼭 추가해줘야 하는 클래스

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/smarteditor2/**").addResourceLocations("classpath:/static/smarteditor2/");
    }
}
