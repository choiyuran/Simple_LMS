package com.itbank.simpleboard.interceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuthInteceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession httpSession = request.getSession();
//        if(Optional.ofNullable(httpSession.getAttribute("user")).isPresent()){
//            return true;
//        } else {
//            response.sendRedirect("/index");
//            return false;
//        }
//    }
}
