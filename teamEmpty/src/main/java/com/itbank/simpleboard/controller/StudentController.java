package com.itbank.simpleboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbank.simpleboard.component.PagingComponent;
import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final UserService userService;
    private final ProfessorService professorService;
    private final ScholarShipService scholarShipService;
    private final SituationRecordService situationRecordService;
    private final PagingComponent pagingComponent;

    @GetMapping("/enroll")
    public ModelAndView enrollList(HttpSession session, String searchType, String keyword,
                                   @PageableDefault(size = 2)Pageable pageable) {
        long startTime = System.currentTimeMillis();

        ModelAndView mav = new ModelAndView("student/home");
        StudentDto student = (StudentDto) session.getAttribute("user");
        if (student == null) {
            mav.addObject("msg", "로그인하세요!");
            mav.setViewName("index");
            return mav;
        }
        Page<LectureDto> list;
        // 키워드 없을 때 전체검색
        if (searchType == null || keyword == null) {
            list = lectureService.selectAll(pageable);
            mav.addObject("list", list);
        } else {
            list = lectureService.selectAll(searchType, keyword, pageable);
            mav.addObject("list", list);
        }

        int start = pagingComponent.calculateStart(list.getNumber());
        int end = pagingComponent.calculateEnd(list.getTotalPages(), start);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);
        mav.addObject("searchType", searchType);
        mav.addObject("keyword", keyword);

        // 등록 - 수강신청(로그인한 학생의 수강목록) - 수강신청한 enrollment를 가져오는거
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
            mav.setViewName("student/enrollment");
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
    public ModelAndView enrollPro(@RequestParam(value = "page", required = false) Integer page, Long stuIdx, Long idx, HttpSession session, RedirectAttributes ra) {
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
            if(page == null){
                mav.setViewName("redirect:/student/enroll");
            }else{
                mav.setViewName("redirect:/student/enroll?page="+page);
            }

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
    @GetMapping("/cancelEnroll")
    @ResponseBody
    public String cancelEnroll(Long stuIdx, Long idx) {
        enrollmentService.cancel(stuIdx, idx);
        return "<script>alert('수강취소 되었습니다!!'); location.href = '/student/enroll';</script>";
    }

    @GetMapping("/modifyCheck")
    public String modifyCheck(HttpSession session) {
        Object login = session.getAttribute("user");
        if (login instanceof StudentDto) {
            return "student/modifyCheck";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/modifyCheck")
    public String modifyCheck(HttpSession session, @RequestParam("password") String password, Model model) {
        String url = "student/modifyCheck";
        Object login = session.getAttribute("user");
        int result = userService.checkPassword(login, password);
        if (result != 0) {
            url = "redirect:/student/studentModify";
        } else {
            model.addAttribute("msg", "비밀번호를 확인해주세요.");
        }
        return url;
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

    @GetMapping("/situation")
    public ModelAndView mySituation(HttpSession session) {
        Object o = session.getAttribute("user");
        ModelAndView mav = new ModelAndView("student/mysituation");
        if(!(o instanceof StudentDto)){
            mav.setViewName("redirect:/login");
        }else{
            mav.addObject("status",situationService.findByStudentIdx(((StudentDto) o).getIdx()).getStudent_status());
        }
        return mav;
    }

    @PostMapping("/genersitu")                               // 일반 휴학
    public String generSitu(HttpSession session, SituationChageDto dto, RedirectAttributes ra) {
        Object o = session.getAttribute("user");
        if(o instanceof StudentDto) {
            StudentDto studentDto = (StudentDto)o;
            dto.setStudent(studentDto.getIdx());
            dto.setStatus(Status_type.일반휴학신청);
            Situation chageSituation = situationService.updateSitu(dto);

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

    @PostMapping("/armysitu")                             // 군 휴학 신청
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

    @PostMapping("/return") // 복학신청
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
        ModelAndView mav = new ModelAndView("student/paymentsList");
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
        ModelAndView mav = new ModelAndView("student/gradeList");

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

    @GetMapping("/overallGrade")
    public ModelAndView overallGradeList(HttpSession session) {
        ModelAndView mav = new ModelAndView("student/overallGrade");
        Object o = session.getAttribute("user");

        if(o instanceof StudentDto) {
            StudentDto dto = (StudentDto) o;
            Long stuIdx = dto.getIdx();

            OverallGradeDto overall= studentService.getOverallGrade(stuIdx);
            GradeSearchConditionDto condition = new GradeSearchConditionDto();

            condition.setSemester(null);
            condition.setStudentIdx(stuIdx);

            List<GradeLectureDto> list = studentService.getLectureDtoList(condition);
            mav.addObject("semesterList", list.stream()
                    .map(GradeLectureDto::getSemester)
                    .distinct()
                    .collect(Collectors.toList()));

            mav.addObject("overall",overall);
            mav.addObject("list",list);
        }
        return mav;
    }

    @ResponseBody
    @PutMapping("/cancel")
    public Map<String, Object> cancelGeneral(@RequestBody Map<String, String> request) {
        log.info("일반 휴학 신청 취소 로직 시작");
        Map<String, Object> responseData = new HashMap<>();
        // 군 휴학 상태를 취소하기 위해서는 일반 휴학 신청 이전의 정보로 다시 되돌려야 한다.
        // 이 정보는 SituationRecord에 있는 가장 최신의 내용으로 바꿔주면 된다.
        Long studentIdx = Long.parseLong(request.get("idx"));

        int row = studentService.changeLatestSituation(studentIdx);

        if(row == 1){
            responseData.put("msg", "휴학 신청이 취소 되었습니다. ");
        }else{
            responseData.put("msg", "휴학 신청을 취소하지 못했습니다.");
        }

        return responseData;
    }

    @ResponseBody
    @PostMapping("/myLecture/data")
    public ResponseEntity<Page<StudentLectureDto>> myLectureListAjax(HttpSession session, @RequestBody LectureSearchConditionDto condition,@PageableDefault(size = 2, sort="idx", direction = Sort.Direction.DESC) Pageable pageable) {
        long startTime = System.currentTimeMillis();

        condition.setStudent_idx(((StudentDto) session.getAttribute("user")).getIdx());
        Page<StudentLectureDto> page = studentService.getStudentLectureDtoListPage(condition, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(page.getTotalPages()));

        long endTime = System.currentTimeMillis();
        log.info("StudentController.myLectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page);
    }

    // 검색조건에 따른 /myLecture
    @GetMapping("/myLecture")   // "교수" 로그인 된 사용자의 본인이 하는 강의 리스트를 보여주는 메서드
    public String myLecture(HttpSession session, Model model, LectureSearchConditionDto condition,@PageableDefault(size = 2, sort="idx", direction = Sort.Direction.DESC) Pageable pageable) {
        Object user = session.getAttribute("user");
        if (user instanceof StudentDto) {
            long startTime = System.currentTimeMillis();

            StudentDto student = (StudentDto) user;
            condition.setProfessor_idx(student.getIdx());
            List<StudentLectureDto> totalLectureDtoList = studentService.getStudentLectureDtoList(condition);
            Page<StudentLectureDto> myLectureDtoList = studentService.getStudentLectureDtoListPage(condition,pageable);
            model.addAttribute("MyList", myLectureDtoList);

            // 각 LectureDto 객체에서 major를 추출하여 중복값 제거 후 Model에 추가
            model.addAttribute("MajorList", totalLectureDtoList.stream()
                    .map(StudentLectureDto::getMajor)
                    .distinct()
                    .collect(Collectors.toList()));

            model.addAttribute("GradeList", totalLectureDtoList.stream()
                    .map(StudentLectureDto::getGrade)
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
            log.info("StudentController.myLecture(Get) 실행 시간: {} 밀리초", endTime - startTime);
            return "student/myLecture";
        } else {
            return "redirect:/login";
        }
    }
}