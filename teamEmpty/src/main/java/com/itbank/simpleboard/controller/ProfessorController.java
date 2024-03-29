package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.Notice;
import com.itbank.simpleboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/professor")
@Slf4j
public class ProfessorController {
    private final ProfessorService professorService;
    private final LectureService lectureService;
    private final AcademicCalendarService academicCalendarService;
    private final GradeService gradeService;
    private final NoticeService noticeService;

    @RequestMapping("/myLecture")   // "교수" 로그인 된 사용자의 본인이 하는 강의 리스트를 보여주는 메서드
    public String myLecture(HttpSession session, Model model, @ModelAttribute LectureSearchConditionDto condition, @PageableDefault(size = 10) Pageable pageable) {
        Object user = session.getAttribute("user");
        if (user instanceof ProfessorDto) {
            long startTime = System.currentTimeMillis();

            ProfessorDto professor = (ProfessorDto) user;
            condition.setProfessor_idx(professor.getProfessor_idx());
            Page<ProfessorLectureDto> myLectureDtoList = professorService.getLectureDtoList(condition, pageable);
            model.addAttribute("MyList", myLectureDtoList);
            model.addAttribute("MajorList", professorService.getMajorNameList(condition));
            model.addAttribute("GradeList", professorService.getGradeList(condition));

            int currentYear = LocalDate.now().getYear();
            List<Integer> yearList = new ArrayList<>();
            for (int i = 4; i >= 0; i--) {
                int year = currentYear - i;
                yearList.add(year);
            }
            model.addAttribute("YearList", yearList);
            model.addAttribute("condition", condition);
            long endTime = System.currentTimeMillis();
            log.info("ProfessorController.myLecture(Get) 실행 시간: {} 밀리초", endTime - startTime);
            return "professor/myLecture";
        } else {
            return "redirect:/login";
        }
    }

    @ResponseBody
    @PutMapping("/planUpload")  // 강의 계획서 업로드용 Ajax 메서드(viewLecture.html)
    public String planUpload(@RequestParam("plan") MultipartFile plan, @RequestParam("lectureIdx") Long lectureIdx) {
        return lectureService.planUpload(plan, lectureIdx);
    }

    @GetMapping("/viewEvaluation/{idx}")    // 내 강의 평가 보기
    public ModelAndView viewEvaluation(@PathVariable("idx") Long idx, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/login");
        Object login = session.getAttribute("user");
        if (login instanceof ProfessorDto) {
            List<EvaluateFormDto> evaluation = professorService.getEvaluation(idx);
            mav.addObject("lecture", lectureService.selectOne(idx));
            if (evaluation != null && !evaluation.isEmpty()) {
                mav.addObject("evaluation", evaluation);
                mav.addObject("total", professorService.countTotalQ1Q2Q3(evaluation));
            }
            mav.setViewName("professor/myLectureEvaluation");
            return mav;
        }
        return mav;
    }

    @GetMapping("/home")    // 교수 홈으로 이동
    public String home(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof ProfessorDto) {
            // home 에서 calendar 불러오기
            List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
            model.addAttribute("calendar", calendar);
            List<Notice> notice = noticeService.findNoticeAll();
            model.addAttribute("notice", notice);
            return "professor/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/professorModify") // 교수 개인 정보 수정
    public String professorModify(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof ProfessorDto) {
            return "professor/professorModify";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/enrollmentList")  // myLecture에서 성적 기입을 눌렀을 때, 수강생 목록을 보여주는 메서드
    public ResponseEntity<List<EnrollmentDto>> enterGrade(@RequestParam("lectureIdx") Long lectureIdx) {
        List<EnrollmentDto> enrollment = professorService.getEnrollmentList(lectureIdx);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(enrollment);
    }

    @ResponseBody
    @PutMapping("/saveGrade")
    public Map<String, Object> saveGrade(@RequestBody Map<String, String> request) {
        Map<String, Object> responseData = new HashMap<>();
        long enrollment_idx = Long.parseLong(request.get("enrollment_idx"));
        int save = gradeService.save(enrollment_idx, request.get("score"));
        responseData.put("result", save);
        if (save != 0) {
            responseData.put("msg", "성적이 입력되었습니다.");
            responseData.put("icon", "success");
            responseData.put("title", "성적입력");
        } else {
            responseData.put("icon", "error");
            responseData.put("title", "성적입력");
            responseData.put("msg", "성적 입력을 실패하였습니다.");
        }
        return responseData;
    }
}
