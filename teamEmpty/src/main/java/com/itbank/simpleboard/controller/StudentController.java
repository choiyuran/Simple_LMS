package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.EnrollmentService;
import com.itbank.simpleboard.service.LectureService;
import com.itbank.simpleboard.service.SituationServive;
import com.itbank.simpleboard.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    private final StudentService studentService;
    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session) {
        ModelAndView mav = new ModelAndView("home");
        UserDTO dto = (UserDTO)session.getAttribute("user");
        List<LectureDto> dtos = lectureService.selectAll();

        StudentDto student = studentService.findByUserIdx(dto.getIdx());

        if(dto != null && dto.getRole().toString().equals("학생")){

            mav.setViewName("student/enrollment");
            mav.addObject("stuIdx", student.getIdx());
            mav.addObject("list",dtos);
        }
        return mav;
    }
    
    // 수강 신청프로세스
    @PostMapping("/enroll")
    public ModelAndView enrollPro(Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
        ModelAndView mav = new ModelAndView("home");
        UserDTO userDto = (UserDTO)session.getAttribute("user");

        Long userIdx = userDto.getIdx();
        if(userDto == null){
            // 로그인 안되어 있으면 로그인 화면으로 이거는 인터셉터에서 처리도 가능하다.
            mav.setViewName("redirect:/");
        }else if(userDto.getRole().toString().equals("학생")){

            Enrollment enrollment = enrollmentService.save(stuIdx, idx);

            if(enrollment != null) {
                ra.addFlashAttribute("msg","수강신청되었습니다.");
            }
            mav.setViewName("redirect:/student/enroll");
        }else{
            // 로그인이 안되어 있거나 학생이 아니야???
            // 학생이 아닌데 어케 수강신청함???? - 너 돌아가
            mav.setViewName("redirect:/side");
        }
        return mav;
    }

    // 수강 취소
    @PostMapping("/cancel")
    public String cancel(Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
//        취소
//        enrollmentService.cancel(userDTO);
        return "redirect:/lectures";
    }

}
