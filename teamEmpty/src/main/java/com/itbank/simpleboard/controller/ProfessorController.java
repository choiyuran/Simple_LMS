package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchConditionDto;
import com.itbank.simpleboard.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/professor")
@Slf4j
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping("/lectureList") // 강의 목록
    public String lectureList(Model model, LectureSearchConditionDto condition) {
        long startTime = System.currentTimeMillis();
        List<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition);
        // LectureDtoList를 Model에 추가
        model.addAttribute("LectureList", lectureDtoList);

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

        int currentYear = LocalDate.now().getYear();
        List<Integer> yearList = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            int year = currentYear - i;
            yearList.add(year);
        }
        model.addAttribute("YearList", yearList);

        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.lectureList(Get) 실행 시간: {} 밀리초", endTime - startTime);
        return "professor/lectureList";
    }

    @ResponseBody
    @PutMapping("/lectureList/data")
    public ResponseEntity<List<ProfessorLectureDto>> lectureListAjax(@RequestBody LectureSearchConditionDto condition) {
        long startTime = System.currentTimeMillis();
        List<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition);
        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.lectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(lectureDtoList);
    }

    @GetMapping("/myLecture")
    public String myLecture(HttpSession session, Model model, LectureSearchConditionDto condition) {
        if (session.getAttribute("professor") == null) {
            return "redirect:/home";
        }
        long startTime = System.currentTimeMillis();

        condition.setProfessor_idx(((ProfessorDto) session.getAttribute("professor")).getProfessor_idx());
        List<ProfessorLectureDto> myLectureDtoList = professorService.getLectureDtoList(condition);
        model.addAttribute("MyList", myLectureDtoList);

        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.myLecture(Get) 실행 시간: {} 밀리초", endTime - startTime);
        return "professor/myLecture";
    }

    @ResponseBody
    @PutMapping("/myLecture/data")
    public ResponseEntity<List<ProfessorLectureDto>> myLectureListAjax(HttpSession session, @RequestBody LectureSearchConditionDto condition) {
        long startTime = System.currentTimeMillis();

        condition.setProfessor_idx(((ProfessorDto) session.getAttribute("professor")).getProfessor_idx());
        List<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition);

        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.myLectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(lectureDtoList);
    }
}
