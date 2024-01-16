package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping("/lectureList") // 강의 목록
    public String lectureList(Model model, LectureSearchCondition condition) {
        long startTime = System.currentTimeMillis();
        List<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition);

        // 각 LectureDto 객체에서 major를 추출하여 중복값 제거 후 Model에 추가
        model.addAttribute("MajorList", lectureDtoList.stream()
                .map(ProfessorLectureDto::getMajor)
                .distinct()
                .collect(Collectors.toList()));

        model.addAttribute("GradeList", lectureDtoList.stream()
                .map(ProfessorLectureDto::getGrade)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

        // LectureDtoList를 Model에 추가
        model.addAttribute("LectureList", lectureDtoList);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + " 밀리초");
        return "professor/lectureList";
    }

    @ResponseBody
    @PutMapping("/lectureList/data")
    public ResponseEntity<List<ProfessorLectureDto>> lectureListAjax(@RequestBody LectureSearchCondition condition) {
        long startTime = System.currentTimeMillis();
        List<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + " 밀리초");
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(lectureDtoList);
    }
}
