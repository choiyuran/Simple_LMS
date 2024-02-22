package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.component.PagingComponent;
import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    private final ManagerService managerService;
    private final LectureRoomService lectureRoomService;
    private final AcademicCalendarService academicCalendarService;
    private final UserService userService;
    private final ProfessorService professorService;
    private final CollegeService collegeService;
    private final SituationService situationService;
    private final NoticeService noticeService;
    private final StudentService studentService;
    private final PagingComponent pagingComponent;

    @GetMapping("/calendar") // 전체 학사일정 조회
    public String calendar(Model model){
        List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
        // Thymeleaf에서 편리하게 사용할 수 있도록 데이터 정리
        Map<Integer, List<AcademicCalendar>> calendarByMonth = calendar.stream()
                .collect(Collectors.groupingBy(cal -> cal.getStart_date().getMonthValue()));
        model.addAttribute("calendarByMonth", calendarByMonth);

        return "common/calendar";
    }

    @GetMapping("/calendarAddForm") // 학사일정 추가
    public String calendarAdd(Model model, HttpSession session){
        model.addAttribute("academicCalendarDto", new AcademicCalendarDto());
        Object user = session.getAttribute("user");
        if(user instanceof ManagerLoginDto) {
            ManagerLoginDto manager = (ManagerLoginDto) user;
            return "manager/calendarAddForm";
        }
        return "home";
    }

    @PostMapping("/calendarAddForm") // 학사일정 추가 Postmapping
    public String calendar(@ModelAttribute("academicCalendarDto") AcademicCalendarDto calendar){
        AcademicCalendar addCalendar = academicCalendarService.addCalendar(calendar);
        return "redirect:/manager/calendar";
    }

    @GetMapping("/calendarEditForm/{id}") // 학사일정 수정 폼
    public String calendarEdit(@PathVariable Long id, Model model, HttpSession session){
        // id를 사용하여 수정할 학사일정 데이터를 데이터베이스에서 가져온다.
        AcademicCalendarDto academicCalendarDto = academicCalendarService.getCalendarById(id);

        // 가져온 데이터를 모델에 담아 수정 폼으로 전달한다.
        model.addAttribute("academicCalendarDto", academicCalendarDto);

        Object user = session.getAttribute("user");
        if(user instanceof ManagerLoginDto) {
            ManagerLoginDto manager = (ManagerLoginDto) user;
            return "manager/calendarEditForm";
        }
        return "home";
    }

    @PostMapping("/calendarEditForm/{id}") // 학사일정 수정 Postmapping
    public String calendarEdit(@PathVariable Long id, @ModelAttribute("academicCalendarDto") AcademicCalendarDto calendar){
        academicCalendarService.editCalendar(id, calendar);

        return "redirect:/manager/calendar";
    }

    @GetMapping("/calendarDelete/{idx}") // 학사일정 삭제
    public String calendarDelete(@PathVariable Long idx){
        academicCalendarService.deleteCalendar(idx);

        return "redirect:/manager/calendar";
    }

    @GetMapping("/managerList") // 교직원 명단 조회
    public ModelAndView list(@PageableDefault(size = 2, sort="idx", direction = Sort.Direction.DESC) Pageable pageable){
        ModelAndView mav = new ModelAndView("manager/managerList");
        Page<ManagerDTO> managerList = managerService.findAllManager(pageable);

        int start = pagingComponent.calculateStart(managerList.getNumber());
        int end = pagingComponent.calculateEnd(managerList.getTotalPages(), start);
        mav.addObject("managerList",managerList);
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);
        return mav;
    }

    @GetMapping("/managerListKeyword")    // 교직원 명단 검색 조회
    public ModelAndView searchList(@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue,
                                   @RequestParam(value = "leave", required = false) Boolean leave,
                                   @PageableDefault(size = 2) Pageable pageable){
        ModelAndView mav = new ModelAndView("manager/managerList");
        HashMap<String, Object> map = new HashMap<>();
        if(searchType != null) {
            map.put("searchType", searchType);
            map.put("searchValue", searchValue);
            map.put("leave", leave);
        }

        Page<ManagerDTO> managerList = managerService.searchManager(map, pageable);
        int start = pagingComponent.calculateStart(managerList.getNumber());
        int end = pagingComponent.calculateEnd(managerList.getTotalPages(), start);
        mav.addObject("map", map);
        mav.addObject("managerList",managerList);
        mav.addObject("searchValue", searchValue);
        mav.addObject("searchType", searchType);
        mav.addObject("leave", leave);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("manager/register");
        mav.addObject("majorList",managerService.selectAllMajor());
        return mav;
    }

    @PostMapping(value = "/addManager", headers = "Content-Type= multipart/form-data")   // 교직원 등록
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerManager(@ModelAttribute UserFormDTO userFormDTO,
                                                               @RequestParam("imageFile") MultipartFile imageFile) {
        Map<String, Object> response = new HashMap<>();

        String msg;
        if (userFormDTO.getUserType().equals("manager")) {
            Manager manager = managerService.addManager(userFormDTO, imageFile);
            if (manager.getIdx() != null) {
                // 교직원 등록 성공 시
                msg = manager.getUser().getUser_name() + "님 교직원 등록완료";
                response.put("success", true);
            } else {
                // 교직원 등록 실패 시
                msg = "교직원 등록에 실패하였습니다";
                response.put("success", false);
            }
        } else {
            // 교직원 타입의 사용자가 아닌 경우
            msg = "교직원 타입의 사용자가 아닙니다";
            response.put("success", false);
        }

        response.put("message", msg);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/addStudent")   // 학생 등록
    public String addStudent() {
        log.info("학생등록페이지");
        return "manager/registerStudent";
    }

    @PostMapping("/addStudent")   // 학생 등록 엑셀 업로드
    public String uploadStudentList(@RequestParam("studentFile")MultipartFile studentFile, Model model) throws IOException {
        log.info("학생등록엑셀업로드");
        if(studentFile.isEmpty()){
            model.addAttribute("message","파일을 업로드해주세요");
            return "manager/registerStudent";
        }


        String fileName = studentFile.getOriginalFilename();

        if (fileName == null || fileName.isEmpty()) {
            model.addAttribute("message", "잘못된 양식의 파일입니다");
            return "manager/registerStudent";
        }
        if(!fileName.contains("학생등록폼")){
            model.addAttribute("message","지정된 양식의 폼이 아닙니다");
            return "manager/registerStudent";

        }

        model.addAttribute("students","학생등록");
        model.addAttribute("studentList",managerService.saveStudentDTOList(studentFile));
        model.addAttribute("collegeList",managerService.selectAllCollege());
        return "manager/registerStudentList";

    }


    @GetMapping("/downloadStudentForm") // 엑셀폼 다운로드
    public ResponseEntity<byte[]> downloadStudentForm() throws IOException {

        // 엑셀 템플릿 파일을 클래스패스에서 로드
        ClassPathResource resource = new ClassPathResource("static/excelForm/studentForm.xlsx");

        // 다운로드할 파일명 설정
        String filename = "학생등록폼(양식변경금지).xlsx";

        // 엑셀 파일 데이터 읽기
        byte[] data = new byte[(int) resource.contentLength()];
        try (InputStream inputStream = resource.getInputStream()) {
            inputStream.read(data);
        }

        // 다운로드할 파일 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentLength(data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @GetMapping("/addStudentList")   // 학생 등록
    public String registerStudentList() {
        log.info("학생등록리스트 확인");
        return "manager/registerStudentList";
    }


    @GetMapping("/registerMajor")               // 학과 등록 페이지로 이동
    public ModelAndView registerMajor() {
        ModelAndView mav = new ModelAndView("manager/registerMajor");
        List<College> list = managerService.selectAllCollege();
        mav.addObject("list", list);
        return mav;
    }

    @PostMapping("/registerMajor")              // 학과 등록
    public String registerMajor(MajorDto major) {
        Major addMajor = managerService.addMajor(major);
        if(addMajor != null) {
            return "redirect:/manager/majorList";
        }
        return "manager/registerMajor";
    }

    @GetMapping("/majorList")       // 학과 목록 (검색어가 있는 경우와 없는 경우 같이 사용)
    public ModelAndView majorList(@RequestParam(value = "collegeIdx", required = false) Long collegeIdx,
                                  @RequestParam(value = "majorName", required = false) String majorName,
                                  @PageableDefault(size = 2, sort = "idx",direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView("manager/majorList");
        Page<Major> list = null;

        // 학과명과 단과대학 이름 둘 다 검색어가 있는 경우
        if (collegeIdx != null  && majorName != null && !majorName.isEmpty()) {
            list = managerService.searchByCollegeAndMajor(collegeIdx, majorName, pageable);
        }
        // 단과대학 이름만 검색어가 있는 경우
        else if (collegeIdx != null) {
            list = managerService.searchByCollege(collegeIdx, pageable);
        }
        // 학과명만 검색어가 있는 경우
        else if (majorName != null && !majorName.isEmpty()) {
            list = managerService.searchByMajorPaging(majorName, pageable);
        }
        // 검색어가 없는 경우, 모든 학과 목록을 반환
        else {
            list = managerService.selectAllMajorPaging(pageable);
        }

        if(list == null) {
            list = Page.empty();
        }

        int start = pagingComponent.calculateStart(list.getNumber());
        int end = pagingComponent.calculateEnd(list.getTotalPages(), start);

        log.info("start : " + start);
        log.info("end : " + end);
        mav.addObject("list", list);
        mav.addObject("majorName", majorName);
        mav.addObject("collegeIdx", collegeIdx);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);

        return mav;
    }

    @GetMapping("/getColleges")             // 학과 검색 ajax
    @ResponseBody
    public List<College> getColleges() {
        return collegeService.getAllColleges();
    }

    @GetMapping("/majorView/{idx}")           // 학과 view
    public ModelAndView majorView(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("manager/majorView");
        Major major = managerService.selectOne(idx);
        mav.addObject("major", major);
        return mav;
    }

    @PostMapping("/majorUpdate/{idx}")           // 학과 정보 수정
    public String majorUpdate(@PathVariable("idx")Long idx, MajorDto param) {
        param.setIdx(idx);
        Major major = managerService.majorUpdate(param);
        if(major != null) {
            return "redirect:/manager/majorList";
        }
        return "redirect:/manager/majorView/" + idx;
    }

    @GetMapping("/majorDelete/{idx}")           // 학과 삭제
    public String majorDelete(@PathVariable("idx")Long idx) {
        Major major = managerService.majorDel(idx);
        return "redirect:/manager/majorList";
    }

    @GetMapping("/lectureAdd")          // 강의 등록 페이지 이동
    public ModelAndView lectureAdd() {
        ModelAndView mav = new ModelAndView("manager/registerLecture");
        List<Major> majorList = managerService.selectAllMajor();
        mav.addObject(majorList);
        return mav;
    }

    @PostMapping("/lectureAdd")         // 강의 등록
    public String lectureAdd(RegisterlectureDto param) {
        Lecture lecture = managerService.addLecture(param);
        if(lecture == null) {
            return "redirect:/manager/lectureAdd";
        }
        return "redirect:/professor/lectureList";
    }

    @ResponseBody
    @GetMapping("/getLecturerooms")             // 학과를 선택하면 해당 학과의 교수와 강의실 조회
    public ResponseEntity<Map<String, Object>> getLecturerooms(@RequestParam("college_idx") Long collegeIdx, @RequestParam("major_idx") Long major_idx) {
        log.info("ResponseEntity");
        List<LectureRoom> lecturerooms = lectureRoomService.getLectureroomsByCollege(collegeIdx);
        List<ProfessorUserDto> professors = professorService.getProfessorsByMajor(major_idx);

        Map<String, Object> response = new HashMap<>();
        response.put("lecturerooms", lecturerooms);
        response.put("professors", professors);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/lectureUpdate/{idx}")             // 강의 수정 페이지 이동
    public ModelAndView lectureUpdate(@PathVariable("idx") Long idx) {
        ModelAndView mav = new ModelAndView("manager/updateLecture");
        Lecture lecture = managerService.selectOneLecture(idx);
        List<Major> majorList = managerService.selectAllMajor();

        // 해당 교수의 user_idx로 user의 정보를 찾음
        UserDTO user = userService.getUserByUserIdx(lecture.getProfessor().getUser().getIdx());
        String professor_name = user.getUser_name();

        mav.addObject("majorList", majorList);
        mav.addObject("lecture", lecture);
        mav.addObject("professor_name", professor_name);
        return mav;
    }

    @PostMapping("/lectureUpdate/{idx}")                // 강의 수정
    public String lectureUpdate(@PathVariable("idx")Long idx, RegisterlectureDto param) {
        Lecture lecture = managerService.updateLecture(param);
        if(lecture == null) {
            return "redirect:/manager/lectureUpdate" + idx;
        }
        return "redirect:/professor/viewLecture/" + idx;
    }

    @GetMapping("/lectureDelete/{idx}")             // 강의 삭제
    public String lectureDelete(@PathVariable("idx")Long idx) {
        Lecture lecture = managerService.delLecture(idx);
        return "redirect:/professor/lectureList";
    }

    @GetMapping("/home")    // 교직원 홈으로 이동
    public String home(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof ManagerLoginDto) {
            // home 에서 calendar 불러오기
            List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
            model.addAttribute("calendar", calendar);
            return "manager/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/studentSituation")                // 학생 상태 조회
    public ModelAndView studentSituation(@RequestParam(required = false) String status,
                                         @PageableDefault(size = 2)Pageable pageable) {
        ModelAndView mav = new ModelAndView("manager/studentSituation");

        // 검색어가 없는 경우에는 모든 학생 목록을 반환하고,
        // 검색어가 있는 경우에는 검색어를 포함하는 학생 목록을 반환
        Page<SituationStuDto> studentList = situationService.selectSituationStu(status, pageable);
        int start = pagingComponent.calculateStart(studentList.getNumber());
        int end = pagingComponent.calculateEnd(studentList.getTotalPages(), start);
        mav.addObject("status", status);
        mav.addObject("studentList", studentList);
        mav.addObject("status", status);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);
        return mav;
    }

    @GetMapping("/studentSituationView/{idx}")              // 학생 상태 변경을 위한 view
    public ModelAndView studentSituationView(@PathVariable("idx") Long idx) {
        ModelAndView mav = new ModelAndView("/manager/studentSituationView");
        SituationStuDto situation = situationService.selectOneSituation(idx);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(situation.getStart_date());
        if(situation.getEnd_date() != null) {
            String end = sdf.format(situation.getEnd_date());
            mav.addObject("end", end);
        }
        mav.addObject("start", start);
        mav.addObject("situation", situation);
        return mav;
    }

    @GetMapping("/studentSituationUpdate/{idx}/{student_idx}")           // 학생 상태 변경
    public String studentSituationUpdate(@PathVariable("idx")Long idx,
                                         @PathVariable("student_idx")Long student_idx,
                                         SituationStuDto param, String start, String end) throws ParseException {
        if(param.getStatus() == null) {
            return "redirect:/manager/studentSituationView/" + idx;
        }
        param.setIdx(idx);
        param.setStudent_idx(student_idx);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = sdf.parse(start);
        java.sql.Date sqldate = new java.sql.Date(start_date.getTime());
        param.setStart_date(sqldate);

        // status가 군휴학 또는 일반휴학일 때만 end_date를 설정
        if(param.getStatus().name().equals("군휴학") || param.getStatus().name().equals("일반휴학")) {
            if(end != null && !end.isEmpty()) {
                Date end_date = sdf.parse(end);
                java.sql.Date sqldate2 = new java.sql.Date(end_date.getTime());
                param.setEnd_date(sqldate2);
            }
        } else {
            param.setEnd_date(null);
        }
        Situation situation = situationService.situationUpdate(param);          // situation 테이블 update
        SituationRecord situationRecord = situationService.situationRecordAdd(param);        // situationRecord 테이블 insert
        return "redirect:/manager/studentSituation";
    }

    @GetMapping("/situationRecord/{idx}")           // 상태 기록 보기
    public ModelAndView situationRecord(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("manager/situationRecord");
        List<SituationRecord> situationRecords = situationService.situationRecordAllByIdx(idx);
        mav.addObject("situationRecord", situationRecords);
        return mav;
    }

    @GetMapping("/professorList")               // 교수 목록 조회
    public ModelAndView professorList(@RequestParam(value = "major_idx", required = false) Long major_idx,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "leave", required = false) Boolean leave,
                                      @PageableDefault(size = 2)Pageable pageable) {
        ModelAndView mav = new ModelAndView("manager/professorList");
        HashMap<String, Object> map = new HashMap<>();
        map.put("major_idx", major_idx);
        map.put("name", name);
        map.put("leave", leave);

        Page<ProfessorListDto> professorList = managerService.searchByMajorAndProfessorAndLeave(map, pageable);
        List<Major> majorList = managerService.selectAllMajor();
        int start = pagingComponent.calculateStart(professorList.getNumber());
        int end = pagingComponent.calculateEnd(professorList.getTotalPages(), start);

        mav.addObject("majorList", majorList);
        mav.addObject("professorList", professorList);
        mav.addObject("map", map);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);
        return mav;
    }

    @GetMapping("/professorView/{idx}")               // 교수 정보 상세보기
    public ModelAndView professorView(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("redirect:/manager/professorList");
        ProfessorListDto professor = managerService.selectOneProfessor(idx);
        if(professor != null) {
            mav.addObject("professor", professor);
            mav.setViewName("manager/professorView");
        }
        return mav;
    }

    @PostMapping("/professorUpdateByManager/{idx}")         // 교직원이 교수 정보 수정 - 입사일
    public String professorUpdateByManager(@PathVariable("idx") Long idx,
                                           @RequestParam("hireDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date hireDate,
                                           @RequestParam("leaveDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date leaveDate) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idx", idx);
        map.put("hireDate", hireDate);
        map.put("leaveDate", leaveDate);

        Professor professor = managerService.updateProfessorByManager(map);
        return "redirect:/manager/professorList";
    }

    @GetMapping("/professorDel/{idx}/{leaveDate}")              // 교수 삭제
    public String professorDel(@PathVariable("idx") Long idx,
                               @PathVariable("leaveDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date leaveDate) {
        HashMap<String, Object> map = new HashMap<>();
        if(leaveDate != null) {
            map.put("leaveDate", leaveDate);
            map.put(("idx"), idx);
        }
        Professor professor = managerService.professorDel(map);
        return "redirect:/manager/professorList";
    }

    @GetMapping("/studentList")         // 학생 목록 조회(검색어 있는 경우와 없는 경우)
    public ModelAndView studentList(@RequestParam(value = "major_idx", required = false) Long major_idx,
                                    @RequestParam(value = "name", required = false) String name,
                                    @PageableDefault(size = 2) Pageable pageable) {
        ModelAndView mav = new ModelAndView("manager/studentList");
        HashMap<String, Object> map = new HashMap<>();
        map.put("major_idx", major_idx);
        map.put("name", name);
        Page<StudentListDto> studentList = managerService.selectAllStudent(map, pageable);
        List<Major> majorList = managerService.selectAllMajor();
        int start = pagingComponent.calculateStart(studentList.getNumber());
        int end = pagingComponent.calculateEnd(studentList.getTotalPages(), start);

        mav.addObject("map", map);
        mav.addObject("majorList", majorList);
        mav.addObject("studentList", studentList);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("maxPage", 5);
        return mav;
    }

    @GetMapping("/studentView/{idx}")             // 학생 상세 보기
    public ModelAndView studentView(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("redirect:/manager/studentList");
        StudentListDto student = managerService.selectOneStudent(idx);
        if(student != null) {
            mav.addObject("student", student);
            mav.setViewName("manager/studentView");
        }
        return mav;
    }

    @PostMapping("/studentUpdateByManager/{idx}")               // 학생 정보 수정(교직원이 입학일만 수정)
    public String studentUpdateByManager(@PathVariable("idx")Long idx,
                                         @RequestParam("entranceDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date entranceDate) {
        Student student = managerService.studentUpdateByManager(idx, entranceDate);
        return "redirect:/manager/studentList";
    }

    @GetMapping("/managerView/{idx}")           // 교직원 상세 보기
    public ModelAndView managerView(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("redirect:/manager/managerList");
        ManagerDTO manager = managerService.selectOneManager(idx);
        if(manager != null) {
            mav.addObject("manager", manager);
            mav.setViewName("manager/managerView");
        }
        return mav;
    }

    @PostMapping("/managerUpdateByManager/{idx}")         // 교직원 정보 수정(교직원이 하는 수정)
    public String managerUpdateByManager(@PathVariable("idx") Long idx,
                                         @RequestParam("hireDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date hireDate,
                                         @RequestParam("leaveDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date leaveDate) {
        Manager manager;
        HashMap<String, Object> map = new HashMap<>();
        if(leaveDate != null) {
            map.put("leaveDate", leaveDate);
        }
        map.put("idx", idx);
        map.put("hireDate", hireDate);
        manager = managerService.updateManagerByManager(map);
        return "redirect:/manager/managerList";
    }

    @GetMapping("/managerDel/{idx}/{leaveDate}")               // 교직원 퇴사 처리
    public String managerDel(@PathVariable("idx")Long idx,
                             @PathVariable("leaveDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date leaveDate) {
        Map<String, Object> map = new HashMap<>();
        if(leaveDate != null) {
            map.put("idx", idx);
            map.put("leaveDate", leaveDate);
            Manager manager = managerService.managerDel(map);
        }
        return "redirect:/manager/managerList";
    }

    @GetMapping("/noticeList")          // 공지 사항 조회
    public ModelAndView noticeList() {
        ModelAndView mav = new ModelAndView("common/noticeList");
        List<Notice> noticeList = noticeService.selectAll();
        mav.addObject("noticeList", noticeList);
        return mav;
    }

    @GetMapping("/noticeView/{idx}")      // 공지 사항 상세보기
    public ModelAndView noticeView(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("common/noticeList");
        Notice notice = noticeService.selectOne(idx);
        if(notice != null) {
            noticeService.increaseViewCount(idx);
            mav.addObject("notice", notice);
            mav.setViewName("common/noticeView");
        }

        return mav;
    }

    @GetMapping("/noticeAdd")            // 공지 사항 등록 페이지 이동
    public String noticeAdd() {
        return "manager/noticeAdd";
    }

    @PostMapping("/noticeAdd")              // 공지 사항 등록
    public String noticeAdd(@RequestParam String title, @RequestParam String content) {
        Map<String,String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        noticeService.noticeAdd(map);
        return "redirect:/manager/noticeList";
    }

    @GetMapping("/noticeUpdate/{idx}")              // 공지사항 수정 페이지로 이동
    public ModelAndView noticeUpdate(@PathVariable("idx")Long idx) {
        ModelAndView mav = new ModelAndView("manager/noticeUpdate");
        Notice notice = noticeService.selectOne(idx);
        if(notice != null) {
            mav.addObject("notice", notice);
        }
        return mav;
    }

    @PostMapping("/noticeUpdate/{idx}")         // 공지사항 수정
    public String noticeUpdate(@PathVariable("idx")Long idx,
                               @RequestParam String title, @RequestParam String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idx", idx);
        map.put("title", title);
        map.put("content", content);
        Notice notice = noticeService.noticeUpdate(map);
        return "redirect:/manager/noticeView/" + idx;
    }

    @GetMapping("/noticeDel/{idx}")           // 공지 사항 삭제
    public String noticeDel(@PathVariable("idx")Long idx) {
        noticeService.noticeDel(idx);
        return "redirect:/manager/noticeList";
    }

    @GetMapping("/managerModify")           // 교직원 개인 정보 수정
    public String managerModify(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof ManagerLoginDto) {
            return "manager/managerModify";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/checkTuitionPayments")    // 교직원 납부 확인
    public String checkTuitionPayment(Model model,CheckTuitionPaymentDto conditions, GradeSearchConditionDto condition) {

        List<CheckTuitionPaymentDto> tuitionPayments = managerService.getCheckTuitionPayment(conditions);

//        Object o = session.getAttribute("user");
//        if(o instanceof StudentDto) {
//            StudentDto dto = (StudentDto) o;
//            condition.setStudentIdx(dto.getIdx());
//            mav.addObject("list",studentService.getLectureDtoList(condition));
//            if(studentService.getLectureDtoList(condition)!= null){
//                mav.addObject("semester", condition.getSemester() == null ? "2024년 1학기" : condition.getSemester());
//            }
//        }else{
//            mav.addObject("redirect:/");
//        }

        model.addAttribute("semester",studentService.getLectureDtoList(condition));
        if(managerService.getCheckTuitionPayment(conditions)!= null){
               model.addAttribute("semester", condition.getSemester() == null ? "2024년 1학기" : condition.getSemester());
            }
        else {
            return "redirect:/";
        }
        model.addAttribute("list", tuitionPayments);

//        System.out.println("result : " + tuitionPayments);
        return "manager/checkTuitionPayments";
    }

    @GetMapping("/pagingTest")          // 페이징 테스트
    public ModelAndView pagingTest(@PageableDefault(size = 5, sort = "idx",direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView("/manager/majorPaging");
        Page<Major> list = managerService.majorListPaging(pageable);
        int start = pagingComponent.calculateStart(list.getNumber());
        int end = pagingComponent.calculateEnd(list.getTotalPages(), start);
        mav.addObject("list", list);
        mav.addObject("num", pageable.getPageNumber() + 1);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("maxPage", 5);
        return mav;
    }

}

