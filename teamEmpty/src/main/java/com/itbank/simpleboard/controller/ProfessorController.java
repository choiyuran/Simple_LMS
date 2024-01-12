package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @RequestMapping("/lectureList") // 강의 목록
    public String lectureList(Model model, LectureSearchCondition condition) {
        model.addAttribute("LectureList", professorService.getLectureListDto(condition));
        return "professor/lectureList";
    }
}
