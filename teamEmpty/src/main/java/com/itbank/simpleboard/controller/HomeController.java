package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.UserRepository;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import com.itbank.simpleboard.service.AcademicCalendarService;
import com.itbank.simpleboard.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final ProfessorRepository professorRepository;
    private final ManagerService managerService;
    private final AcademicCalendarService academicCalendarService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
//        에러 테스트용 예외발생 코드
//        int n = 10 / 0;
//        System.out.println(n);

        // home 에서 calendar 불러오기
        List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();

        model.addAttribute("calendar", calendar);

        return "home";
    }

    @GetMapping("/test1")
    public String test1() {
        return "manager/registerLecture";
    }

    @GetMapping("/test2")
    public String test2() {
        return "manager/registerLectureEvaluationStu";
    }

    @GetMapping("/test3")
    public String test3() {
        return "manager/registerMajor";
    }

    @GetMapping("/test4")
    public String test4() {
//        int n = 2/0;
        return "manager/studentStatManage";
    }

    @GetMapping("/list")
    public String list(){
        return "student/lectureList";
    }

    @GetMapping("/side")
    public String side(){
        return "layout/sidebar";
    }


    @GetMapping("/err")
    public String error() {
        int n = 10 / 0;
        System.out.println(n);
        return "home";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("common/register");
        mav.addObject("majorList",managerService.selectAllMajor());
        return mav;
    }

    // 테스트용 학생 로그인
    @GetMapping("/logintest")
    public String loginTest(HttpSession session) {
        User user = userRepository.findById(7L).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setIdx(user.getIdx());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setPnum(user.getPnum());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        session.setAttribute("user", userDTO);
        return "home";
    }
    
    @GetMapping("/logouttest")
    public String logouttest(HttpSession session) {
        session.invalidate();
        return "home";
    }

    // 테스트용 교수 로그인
    @GetMapping("/ProfessorLogin")
    public String ProfessorTestLogin(HttpSession session) {
        long startTime = System.currentTimeMillis();
        User user = userRepository.findById(1L).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setUser_pw(user.getUser_pw());
        userDTO.setSalt(user.getSalt());
        userDTO.setIdx(user.getIdx());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setPnum(user.getPnum());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());

        ProfessorDto dto = new ProfessorDto();
        Professor professor = professorRepository.findByUser(user);

        MajorDto majorDto = new MajorDto();
        majorDto.setIdx(professor.getMajor().getIdx());
        majorDto.setName(professor.getMajor().getName());
        majorDto.setTuition(professor.getMajor().getTuition());
        majorDto.setCollege_idx(professor.getMajor().getCollege().getIdx());
        majorDto.setAbolition(professor.getMajor().getAbolition());

        dto.setUser(userDTO);
        dto.setProfessor_idx(professor.getProfessor_idx());
        dto.setImg(professor.getProfessor_img());
        dto.setHireDate(professor.getHireDate());
        dto.setMajor(majorDto);

        session.setAttribute("professor", dto);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + " 밀리초");
        return "redirect:/professor/myLecture";
    }
}
