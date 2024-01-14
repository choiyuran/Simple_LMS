package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    // 처음엔 RequestMapping으로 Get, Post 모두 현재 2번 방식으로 처리하려고 했음
    // 그런데 돌려보니, 처음 Get에서 2300 밀리초 이상의 시간이 걸리고, 검색 역시 비슷한 시간이 걸렸음
    // 그래서 쿼리를 두 번 내보내지 않기 위해 현재 1번 방식의 메서드를 작성했고, 그 결과 1300 밀리초로 시간이 크게 단축됐음
    // 하지만 문제점이, List를 뽑아온 상태로 MajorList를 추출하다 보니, 그 다음 major 목록이 제대로 출력되지 않음
    // ex) 처음 Get에서는 9개의 전공 모두 출력 -> 신경과 검색 -> 목록이 나온 후 전공 선택 창에 "신경과"만 존재

    @GetMapping("/lectureList") // 강의 목록
    public String lectureList1(Model model, LectureSearchCondition condition) {
        long startTime = System.currentTimeMillis();
        List<LectureDto> lectureDtoList = professorService.getLectureDtoList(condition);

        // 각 LectureDto 객체에서 major를 추출하여 중복값 제거 후 Model에 추가
        model.addAttribute("MajorList", lectureDtoList.stream()
                .map(LectureDto::getMajor)
                .distinct()
                .collect(Collectors.toList()));

        // LectureDtoList를 Model에 추가
        model.addAttribute("LectureList", lectureDtoList);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + " 밀리초");
        return "professor/lectureList";
    }

    @PostMapping("/lectureList") // 강의 목록
    public String lectureList2(Model model, LectureSearchCondition condition) {
        long startTime = System.currentTimeMillis();
        model.addAttribute("LectureList", professorService.getLectureDtoList(condition));
        model.addAttribute("MajorList", professorService.findAllMajorNames());
        model.addAttribute("typeSelect", condition.getType());
        model.addAttribute("yearSelect", condition.getYear());
        model.addAttribute("semesterSelect", condition.getSemester());
        model.addAttribute("gradeSelect", condition.getGrade());
        model.addAttribute("majorSelect", condition.getMajor());
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + " 밀리초");
        return "professor/lectureList";
    }
}
