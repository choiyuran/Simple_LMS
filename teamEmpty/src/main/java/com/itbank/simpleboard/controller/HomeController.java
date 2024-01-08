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
        return "common/register";
    }

}
