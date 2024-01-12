package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    
    @GetMapping("/registerMajor")                 // 학과 등록 페이지로 이동
    public ModelAndView registerMajor() {
        ModelAndView mav = new ModelAndView("manager/registerMajor");
        List<College> list = managerService.selectAllCollege();
        mav.addObject("list", list);
        return mav;
    }

    @PostMapping("/registerMajor")              // 학과 등록
    public String registerMajor(MajorDto major) {
        Major addMajor = managerService.addMajor(major);
        if(addMajor != null) {
            return "/home";
        }
        return "manager/registerMajor";
    }

    @GetMapping("/majorList")       // 학과 목록
    public ModelAndView majorList() {
        ModelAndView mav = new ModelAndView("manager/majorList");
        List<Major> list = managerService.selectAllMajor();
        mav.addObject("list", list);
        return mav;
    }



}
