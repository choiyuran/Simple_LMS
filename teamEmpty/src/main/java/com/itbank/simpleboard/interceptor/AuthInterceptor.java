//package com.itbank.simpleboard.interceptor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.Optional;
//
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession httpSession = request.getSession();
//        if(Optional.ofNullable(httpSession.getAttribute("user")).isPresent()){
//            return true;
//        } else {
//            response.sendRedirect("/");
//            return false;
//        }
//    }
//}
