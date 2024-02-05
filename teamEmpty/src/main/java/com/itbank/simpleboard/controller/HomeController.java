package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.user.UserRepository;
import com.itbank.simpleboard.service.AcademicCalendarService;
import com.itbank.simpleboard.service.FileService;
import com.itbank.simpleboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AcademicCalendarService academicCalendarService;
    private final FileService fileService;

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



    // 테스트용 학생 로그인
    @GetMapping("/logintest")
    public String loginTest(HttpSession session) {
        User user = userRepository.findById(4L).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setIdx(user.getIdx());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setPnum(user.getPnum());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setUser_address(user.getAddress());
        session.setAttribute("user", userDTO);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String user_id, @RequestParam String user_pw, HttpSession session) {
        String url = "home";
        UserDTO user = userService.getUser(user_id, user_pw);
        switch (user.getRole().toString()) {
            case "교수":
                ProfessorDto professor = userService.getProfessor(user);
                session.setAttribute("user", professor);
                url = "redirect:/professor/home";
                break;
            case "학생":
                session.setAttribute("user", userService.getStudent(user));
                url = "redirect:/student/home";
                break;
            case "교직원":
                ManagerLoginDto manager = userService.getManager(user);
                session.setAttribute("user", manager);
                url = "redirect:/manager/home";
                break;
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
