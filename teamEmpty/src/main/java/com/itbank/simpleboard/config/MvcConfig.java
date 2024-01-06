package com.itbank.simpleboard.config;

import com.itbank.simpleboard.interceptor.AuthInteceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer { // 로그인 안돼있으면
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInteceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/login","/signup");
//    }
}
