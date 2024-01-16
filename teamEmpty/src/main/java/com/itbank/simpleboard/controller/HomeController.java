package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
//        에러 테스트용 예외발생 코드
//        int n = 10 / 0;
//        System.out.println(n);
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
    public String register() {
        return "common/register";
    }

    // 테스트용 학생 로그인
    @GetMapping("/logintest")
    public String loginTest(HttpSession session) {
        User user = userRepository.findById(8L).get();
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



    @GetMapping("/studentModify")
    public String studentModify() {
        return "student/studentModify";
    }

}
