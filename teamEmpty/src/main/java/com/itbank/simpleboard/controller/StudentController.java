package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.SituationServive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    //  수강신청
    private final SituationServive situationServive;

    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session) {
        ModelAndView mav = new ModelAndView("home");
        // 수강신청 리스트
        UserDTO dto = (UserDTO)session.getAttribute("user");

        if(dto != null && dto.getRole().toString().equals("학생")){
            mav.setViewName("student/mysituation");
        }
        return mav;
    }
    
    // 수강 신청프로세스
    @PostMapping("/enroll")
    public Situation enrollPro() {
        // 이거 그냥 학생이랑
        return null;
    }

}
