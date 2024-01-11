package com.itbank.simpleboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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


    @GetMapping("/studentModify")
    public String studentModify() {
        return "student/studentModify";
    }

}
