package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    //  수강신청
    private final EnrollmentService enrollmentService;
    private final LectureService lectureService;
    private final StudentService studentService;
    private final EvaluationService evaluationService;
    private final AcademicCalendarService academicCalendarService;
    private final SituationService situationService;
    private final PaymentsService paymentsService;
    private final MajorService majorService;
    private final ScholarShipAwardService scholarShipAwardService;

    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session, String searchType, String keyword) {
        long startTime = System.currentTimeMillis();

        ModelAndView mav = new ModelAndView("student/home");
        StudentDto student = (StudentDto) session.getAttribute("user");
        if (student == null) {
            mav.addObject("msg", "로그인하세요!");
            mav.setViewName("index");
            return mav;
        }

        if (searchType == null || keyword == null) {
            mav.addObject("list", lectureService.selectAll());
        } else {
            mav.addObject("list", lectureService.selectAll(searchType, keyword));
        }

        List<Enrollment> enrollmentList = enrollmentService.findByStudent(student.getIdx());
        List<LectureDto> lectureList = new ArrayList<>();

        for (Enrollment e : enrollmentList) {
            LectureDto lecturedto = new LectureDto();
            lecturedto.setCredit(e.getLecture().getCredit());
            lecturedto.setDay(e.getLecture().getDay());
            lecturedto.setStart(e.getLecture().getStart());
            lecturedto.setEnd(e.getLecture().getEnd());
            lecturedto.setIdx(e.getLecture().getIdx());
            lecturedto.setGrade(e.getLecture().getGrade());
            lecturedto.setIntro(e.getLecture().getIntro());
            lecturedto.setPlan(e.getLecture().getPlan());
            lecturedto.setName(e.getLecture().getName());
            lecturedto.setType(e.getLecture().getType().toString());
            lecturedto.setVisible(e.getLecture().getVisible().toString());
            lecturedto.setProfessor(e.getLecture().getProfessor().getProfessor_idx());
            lecturedto.setSemester(e.getLecture().getSemester());
            lecturedto.setMaxCount(e.getLecture().getMaxCount());
            lecturedto.setCurrentCount(e.getLecture().getCurrentCount());
            lecturedto.setProfessor_name(e.getLecture().getProfessor().getUser().getUser_name());
            lecturedto.setAbolition(e.getLecture().getAbolition().toString());
            lectureList.add(lecturedto);
        }

        if(student.getUser().getRole().toString().equals("학생")){
            mav.setViewName("/student/enrollment");
            mav.addObject("lectureList", lectureList);
            mav.addObject("stuIdx", student.getIdx());
        }

        log.info("모델테스트 : "+mav.getModel().toString());

        long endTime = System.currentTimeMillis();
        log.info("총 실행시간 : " + (endTime - startTime));
        return mav;
    }

    // 수강 신청프로세스
    @PostMapping("/enroll")
    public ModelAndView enrollPro(Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
        long startTime = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("student/home");
        StudentDto studentDto = (StudentDto) session.getAttribute("user");
        if (studentDto == null) {
            // 로그인 안되어 있으면 로그인 화면으로 이거는 인터셉터에서 처리도 가능하다.
            mav.setViewName("redirect:/");
        } else if (studentDto.getUser().getRole().toString().equals("학생")) {
            Enrollment enrollment = enrollmentService.save(stuIdx, idx);
            if (enrollment != null) {
                ra.addFlashAttribute("msg", "수강신청되었습니다.");
            } else {
                ra.addFlashAttribute("msg", "수강신청에 실패했습니다.");
            }
            mav.setViewName("redirect:/student/enroll");
        } else {
            // 로그인이 안되어 있거나 학생이 아니야???
            // 학생이 아닌데 어케 수강신청함???? - 너 돌아가
            mav.setViewName("redirect:/side");
        }
        long endTime = System.currentTimeMillis();
        log.info("수강신청 실행시간 : " + (endTime - startTime) / 1000 + "초");
        return mav;
    }

    // 수강 취소
    @GetMapping("/cancel")
    @ResponseBody
    public String cancel(Long stuIdx, Long idx) {
        enrollmentService.cancel(stuIdx, idx);
        return "<script>alert('수강취소 되었습니다!!'); location.href = '/student/enroll';</script>";
    }

    // 내 정보 수정
    @GetMapping("/studentModify")
    public ModelAndView myPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("student/studentModify");
        StudentDto studentDto = (StudentDto) session.getAttribute("user"); // 형변환
        mav.addObject("dto", studentDto);
        return mav;
    }

    @GetMapping("/evaluationList")              // 강의 평가 목록
    public ModelAndView evaluationList(HttpSession session) {
        long startTime = System.currentTimeMillis();
        ModelAndView mav = new ModelAndView("student/home");

        StudentDto studentDto = (StudentDto) session.getAttribute("user");
        if (studentDto != null) {
            List<EnrollmentDto> enrollmentList = enrollmentService.findByStudentAll(studentDto.getIdx());

            if (!enrollmentList.isEmpty()) {
                mav.addObject("list", enrollmentList);
                mav.setViewName("student/evaluationList");
            } else {
                mav.addObject("msg", "평가할 강의가 없습니다.");
            }
        }
        long endTime = System.currentTimeMillis();

        log.info("총 시간 : " + (endTime - startTime));
        return mav;
    }

    @GetMapping("/evaluate/{idx}")              // 강의 평가지 페이지로 이동
    public ModelAndView evaluateView(@PathVariable("idx") Long idx) {
        ModelAndView mav = new ModelAndView("student/registerLectureEvaluationStu");
        EvaluationDto dto = evaluationService.findByIdx(idx);
        mav.addObject("dto", dto);
        return mav;
    }

    @PostMapping("/evaluate/{idx}")                 // 강의 평가 등록
    public ModelAndView evaludatePro(@PathVariable("idx") Long idx, EvaluateFormDto evaluateFormDto) {
        ModelAndView mav = new ModelAndView("student/home");
        Evaluation evaluation = evaluationService.save(evaluateFormDto);
        if (evaluation != null) {
            mav.addObject("msg", "평가 등록 완료");
            mav.setViewName("redirect:/student/evaluationList");
        }
        return mav;
    }

    @GetMapping("/home")    // 학생 홈으로 이동
    public String home(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof StudentDto) {
            // home 에서 calendar 불러오기
            List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
            model.addAttribute("calendar", calendar);
            return "student/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("situation")
    public ModelAndView mySituation(HttpSession session) {
        Object o = session.getAttribute("user");
        ModelAndView mav = new ModelAndView("student/mysituation");
        if(!(o instanceof StudentDto)){
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @PostMapping("genersitu")                               // 일반 휴학
    public String generSitu(HttpSession session, SituationChageDto dto, RedirectAttributes ra) {
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto studentDto = (StudentDto)o;
            dto.setStudent(studentDto.getIdx());
            dto.setStatus(Status_type.일반휴학신청);
            Situation chageSituation = situationService.updateSitu(dto);
            System.err.println("dto : " + dto);
            if(chageSituation != null){
                ra.addFlashAttribute("msg", "일반 휴학 신청 완료");
            }else{
                ra.addFlashAttribute("msg", "일반 휴학 신청 실패");
            }
            return "redirect:/student/situation";
        }else{
            ra.addFlashAttribute("msg", "학생 로그인 상태가 아닙니다.");
            return "redirect:/login";
        }
    }

    @PostMapping("armysitu")                             // 군 휴학 신청
    public String armySitu(HttpSession session, SituationChageDto dto,RedirectAttributes ra) {
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto studentDto = (StudentDto)o;
            dto.setStudent(studentDto.getIdx());
            dto.setStatus(Status_type.군휴학신청);
            System.err.println("dto : " + dto);
            Situation chageSituation = situationService.updateSitu(dto);
            if(chageSituation != null){
                ra.addFlashAttribute("msg", "군 휴학 신청 완료");
            }else{
                ra.addFlashAttribute("msg", "군 휴학 신청 실패");
            }
            return "redirect:/student/situation";
        }else{
            ra.addFlashAttribute("msg", "학생 로그인 상태가 아닙니다.");
            return "redirect:/login";
        }
    }

    @PostMapping("return") // 복학신청
    public String returnSitu(HttpSession session,SituationChageDto dto,RedirectAttributes ra) {
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto studentDto = (StudentDto)o;
            dto.setStudent(studentDto.getIdx());
            dto.setStatus(Status_type.복학신청);
            Situation chageSituation = situationService.updateSitu(dto);
            if(chageSituation != null){
                ra.addFlashAttribute("msg","복학 신청 완료");
            }else{
                ra.addFlashAttribute("msg", "복학 신청 실패");
            }
            return "redirect:/student/situation";
        }else{
            ra.addFlashAttribute("msg", "학생 로그인 상태가 아닙니다.");
            return "redirect:/login";
        }
    }

    // 등록금 납부하기 전 내역을 나타내는 페이지
    @GetMapping("/paymentTuition")
    public ModelAndView paymentTuition(HttpSession session) {
        ModelAndView mav = new ModelAndView("student/paymentTuition");
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            Integer tuition = majorService.getTuition(((StudentDto) o).getIdx());
            Integer totalScholarship = scholarShipAwardService.getTotal(((StudentDto) o).getIdx());
            if(tuition < totalScholarship) {
                mav.addObject("totalTuition", 0);
            }else{
                mav.addObject("totalTuition", tuition-totalScholarship);
            }
            mav.addObject("tuition", tuition);
            mav.addObject("totalScholarship", totalScholarship);
            mav.addObject("semester", "2024학년 1학기");
        }else{
            session.invalidate();
            mav.addObject("msg", "다시 학생로그인 하세요");
            mav.setViewName("/home");
        }
        return mav;
    }

    @GetMapping("/paymentTuition/{idx}")
    public ModelAndView paymentTuitionByIdx(@PathVariable("idx") Long idx, HttpSession session) {
        ModelAndView mav = new ModelAndView("student/paymentTuition");
        Payments payments = paymentsService.findById(idx);
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto && payments != null) {
            Integer tuition = majorService.getTuition(((StudentDto) o).getIdx());
            Integer totalScholarship = scholarShipAwardService.getTotalByPayments(payments.getIdx(), ((StudentDto) o).getIdx());
            if(tuition < totalScholarship) {
                mav.addObject("totalTuition", 0);
            }else{
                mav.addObject("totalTuition", tuition-totalScholarship);
            }
            mav.addObject("semester", payments.getSemester());
            mav.addObject("tuition", tuition);
            mav.addObject("totalScholarship", totalScholarship);
        }else{
            session.invalidate();
            mav.addObject("msg", "다시 학생로그인 하세요");
            mav.setViewName("/home");
        }
        return mav;
    }

    // 등록금 납부 수행 컨트롤러
    @PostMapping("/paymentTuition")
    public String paymentTuitionPro(PaymentsDto dto, RedirectAttributes ra) {
        Payments payments = paymentsService.save(dto);

        if(payments != null){
            ra.addFlashAttribute("msg","등록금 납부 완료");
            return "redirect:/student/home";
        }
        ra.addFlashAttribute("msg", "등록금 납부 실패");
        return "redirect:/student/paymentTuition";
    }

    // 등록금 납부 내역 리스트
    @GetMapping("/paymentsList")
    public ModelAndView paymentsList(HttpSession session, RedirectAttributes ra) {
        ModelAndView mav = new ModelAndView("/student/paymentsList");
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto dto = (StudentDto) o;
            List<PaymentsListDto> dtos = paymentsService.getList(dto.getIdx());
            System.err.println("Controller:" + dto);
            mav.addObject("list", dtos);
        }else{
            ra.addFlashAttribute("msg", "학생 전용 페이지 입니다.");
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @GetMapping("/gradeList")
    public ModelAndView GradeList(GradeSearchConditionDto condition, HttpSession session) {
        ModelAndView mav = new ModelAndView("/student/gradeList");

        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto dto = (StudentDto) o;
            condition.setStudentIdx(dto.getIdx());
            List<GradeLectureDto> list = studentService.getLectureDtoList(condition);
            mav.addObject("semesterList", list.stream()
                    .map(GradeLectureDto::getSemester)
                    .distinct()
                    .collect(Collectors.toList()));
            mav.addObject("list",list);
            if(studentService.getLectureDtoList(condition)!= null){
                mav.addObject("semester", condition.getSemester() == null ? "2024년 1학기" : condition.getSemester());
            }
        }else{
            mav.addObject("redirect:/login");
        }
        return mav;
    }

    @ResponseBody
    @PutMapping("/gradeList/data")    // select 결과에 따라 반환하는 Ajax용 메서드(lectureList.html)
    public ResponseEntity<List<GradeLectureDto>> lectureListAjax(HttpSession session, @RequestBody GradeSearchConditionDto condition) {
        long startTime = System.currentTimeMillis();
        condition.setStudentIdx(((StudentDto)session.getAttribute("user")).getIdx());
        List<GradeLectureDto> gradeLectureDtoList = studentService.getLectureDtoList(condition);

        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.myLectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(gradeLectureDtoList);
    }


    // 등록금 고지서
    @GetMapping("/tuitionBill")
    public String tuitionBill(HttpSession session, Model model){

        // 세션에 담긴 idx 를 이용해 해당 학생의 등록금 고지서를 불러오기
        StudentDto dto = (StudentDto) session.getAttribute("user");

        // 세션에 user가 없으면 로그인 페이지로 이동
        if(dto == null){
            return "redirect:/login";
        }
        // 학생의 정보 가져오기
        Long studentIdx = dto.getIdx();

        List<TuitionDto> tuitionDataList = studentService.getTuitionData(studentIdx);

        // 모델에 추가
        model.addAttribute("tuitionDataList", tuitionDataList);

        return "student/tuitionBill";
    }
}