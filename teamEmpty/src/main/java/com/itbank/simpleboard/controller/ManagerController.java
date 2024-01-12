package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;


    @GetMapping("/managerList") // 교직원 명단 조회
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("manager/managerList");
        List<ManagerDTO> managerList = managerService.findAllManager();
        mav.addObject("managerList",managerList);
        return mav;
    }
    @PostMapping("/managerList")    // 교직원 명단 검색 조회
    public ModelAndView searchList(@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue){
        ModelAndView mav = new ModelAndView("manager/managerList");
        List<ManagerDTO> managerList = managerService.findAllManager();
        mav.addObject("managerList",managerList);
        return mav;
    }

    @GetMapping("/registerMajor")
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
