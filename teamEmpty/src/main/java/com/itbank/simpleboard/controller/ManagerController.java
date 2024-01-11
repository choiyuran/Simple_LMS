package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/calendar")
    public String calendar(Model model){
        List<AcademicCalendar> calendar = managerService.findAll();
        model.addAttribute("calendar", calendar);
        return "common/calendar";
    }


}
