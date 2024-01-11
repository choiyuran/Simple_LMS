package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping("/lectureList")
    public String lectureList(Model model) {
        return "professor/lectureList";
    }
}
