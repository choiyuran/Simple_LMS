package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.EnrollmentService;
import com.itbank.simpleboard.service.LectureService;
import com.itbank.simpleboard.service.SituationServive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {


    private final SituationServive situationServive;
    //  수강신청
    private final EnrollmentService enrollmentService;

    private final LectureService lectureService;
    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session) {
        ModelAndView mav = new ModelAndView("home");
        UserDTO dto = (UserDTO)session.getAttribute("user");
        List<LectureDto> dtos = lectureService.selectAll();


        if(dto != null && dto.getRole().toString().equals("학생")){
            mav.setViewName("student/enrollment");
            mav.addObject("list",dtos);
        }
        return mav;
    }
    
    // 수강 신청프로세스
    @PostMapping("/enroll")
    public String enrollPro(LectureDto dto, HttpSession session) {
        Long lectureIdx = dto.getIdx();
        UserDTO user = (UserDTO)session.getAttribute("user");
        if(user != null && user.getRole().toString().equals("학생")){
            // 학생 서비스에서 학생을 받아서 수강신청 로직을 실행해야한다.
//            StudentDto dto =
        }
        Long userIdx = user.getIdx();
//        lectureService.enroll(lectureIdx,userIdx);
        return "home";
    }

//    @GetMapping("/enrollDetail/{idx}")
//    public String enrollDetail(@PathVariable("idx") Long idx) {
//        return "home";
//    }

}
