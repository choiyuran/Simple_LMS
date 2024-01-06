package com.itbank.simpleboard.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req,HttpServletResponse res, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());

        Integer status = 200;
        // 상태코드 반환하기
        // 상태코드 직접 설정하기
        if (e instanceof ArithmeticException) {
           res.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            status =res.getStatus();
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            status = res.getStatus();
        }

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            mav.addObject("statusCode", statusCode);
        }

        mav.setViewName("/common/errorPage");
        return mav;
    }
}