package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/managerList")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("manager/managerList");
        List<Manager> managerList = managerService.findAll();
        mav.addObject("managerList",managerList);
        return mav;
    }


}
