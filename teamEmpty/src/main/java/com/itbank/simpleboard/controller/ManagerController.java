package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.service.*;
import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.dto.RegisterlectureDto;
import com.itbank.simpleboard.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    private final ManagerService managerService;
    private final LectureRoomService lectureRoomService;
    private final AcademicCalendarService academicCalendarService;
    private final UserService userService;
    private final HttpSession session;
    private final ProfessorService professorService;
    private final CollegeService collegeService;
    private final SituationServive situationServive;

    @GetMapping("/calendar") // 전체 학사일정 조회
    public String calendar(Model model){
        List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
        // Thymeleaf에서 편리하게 사용할 수 있도록 데이터 정리
        Map<Integer, List<AcademicCalendar>> calendarByMonth = calendar.stream()
                .collect(Collectors.groupingBy(cal -> cal.getStart_date().getMonthValue()));
        model.addAttribute("calendarByMonth", calendarByMonth);

        // 세션에서 사용자 정보 가져오기
        UserDTO user = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", user);

        return "common/calendar";
    }

    @GetMapping("/calendarAddForm") // 학사일정 추가
    public String calendarAdd(Model model){
        model.addAttribute("academicCalendarDto", new AcademicCalendarDto());
        UserDTO user = (UserDTO) session.getAttribute("user");

        System.out.println(user.getRole());
        if(user.getRole().equals(User_role.교직원)) {
            return "manager/calendarAddForm";
        }
        else{
            return "home";
        }
    }

    @PostMapping("/calendarAddForm") // 학사일정 추가 Postmapping
    public String calendar(@ModelAttribute("academicCalendarDto") AcademicCalendarDto calendar){
        AcademicCalendar addCalendar = academicCalendarService.addCalendar(calendar);
        return "redirect:/manager/calendar";
    }

    @GetMapping("/calendarEditForm/{id}") // 학사일정 수정 폼
    public String calendarEdit(@PathVariable Long id, Model model){
        // id를 사용하여 수정할 학사일정 데이터를 데이터베이스에서 가져온다.
        AcademicCalendarDto academicCalendarDto = academicCalendarService.getCalendarById(id);

        // 가져온 데이터를 모델에 담아 수정 폼으로 전달한다.
        model.addAttribute("academicCalendarDto", academicCalendarDto);

        UserDTO user = (UserDTO) session.getAttribute("user");

        if(user.getRole().equals(User_role.교직원)) {
            return "manager/calendarEditForm";
        }
        else{
            return "home";
        }
    }

    @PostMapping("/calendarEditForm/{id}") // 학사일정 수정 Postmapping
    public String calendarEdit(@PathVariable Long id, @ModelAttribute("academicCalendarDto") AcademicCalendarDto calendar){
        academicCalendarService.editCalendar(id, calendar);

        return "redirect:/manager/calendar";
    }

    @GetMapping("/managerList") // 교직원 명단 조회
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("manager/managerList");
        List<ManagerDTO> managerList = managerService.findAllManager();
        mav.addObject("managerList",managerList);
        return mav;
    }

    @PostMapping("/managerList")    // 교직원 명단 검색 조회
    public ModelAndView searchList(@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue){
        ModelAndView mav = new ModelAndView("manager/managerList");
        List<ManagerDTO> managerList = managerService.searchManager(searchType,searchValue);
        mav.addObject("managerList",managerList);
        return mav;
    }

    @PostMapping("/addManager")   // 교직원 등록
    public ResponseEntity<Map<String, String>> registerManager(@ModelAttribute UserFormDTO userFormDTO) {
        try {
            log.info("교직원등록");
            long startTime = System.currentTimeMillis();
            log.info(userFormDTO.getUserType());
            log.info(userFormDTO.getEmail());
            Map<String, String> response = new HashMap<>();

            if(userFormDTO.getUserType().equals("manager")){
                Manager manager = managerService.addManager(userFormDTO);
                if(manager.getIdx() != null){
                    // 응답 생성
                    response.put("message", "폼 등록이 완료되었습니다.");
                    response.put("name", manager.getUser().getUser_name());
                    response.put("type", manager.getUser().getRole().toString());
                }
            }


            long endTime = System.currentTimeMillis();
            log.info("ProfessorController.lectureListAjax 실행 시간: {} 밀리초", endTime - startTime);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("교직원 등록 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addstudent")   // 학생 등록
    public String addStudent() {
        log.info("학생등록");
        return "common/register";
    }

    @PostMapping("/addProfessor")   // 교수 등록

    @ResponseBody// 교수 등록
    public ResponseEntity<Map<String, String>> registerProfessor(@ModelAttribute UserFormDTO userFormDTO) {
        try {
            log.info("교수등록");
            long startTime = System.currentTimeMillis();
            // yourService.processForm(formDTO);
            log.info(userFormDTO.getUserType());
            log.info(userFormDTO.getEmail());
            log.info(String.valueOf(userFormDTO.getMajor()));
            Map<String, String> response = new HashMap<>();

            if(userFormDTO.getUserType().equals("professor")){
                Professor professor = managerService.addProfessor(userFormDTO);
                if(professor.getProfessor_idx() != null){
                    // 응답 생성
                    response.put("message", "폼 등록이 완료되었습니다.");
                    response.put("name", professor.getUser().getUser_name());
                    response.put("type", professor.getUser().getRole().toString());
                    log.info(professor.getUser().getUser_name());
                }
                else{
                    response.put("message", "교수 등록에 실패하였습니다");
                    log.info("교수등록 실패");
                }
            }
            log.info(response.toString());

            long endTime = System.currentTimeMillis();
            log.info("ProfessorController.lectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(response);
        } catch (Exception e) {
            log.error("교수 등록 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
            return "/home";
        }
        return "manager/registerMajor";
    }

    @GetMapping("/majorList")       // 학과 목록 (검색어가 있는 경우와 없는 경우 같이 사용)
    public ModelAndView majorList(@RequestParam(value = "collegeIdx", required = false) Long collegeIdx,
                                  @RequestParam(value = "majorName", required = false) String majorName) {
        ModelAndView mav = new ModelAndView("manager/majorList");
        log.info("collegeIdx: " + collegeIdx + ", majorName: " + majorName);
        List<Major> list;

        // 학과명과 단과대학 이름 둘 다 검색어가 있는 경우
        if (collegeIdx != null  && majorName != null && !majorName.isEmpty()) {
            list = managerService.searchByCollegeAndMajor(collegeIdx, majorName);
        }
        // 단과대학 이름만 검색어가 있는 경우
        else if (collegeIdx != null) {
            list = managerService.searchByCollege(collegeIdx);
        }
        // 학과명만 검색어가 있는 경우
        else if (majorName != null && !majorName.isEmpty()) {
            list = managerService.searchByMajor(majorName);
            log.info("major : " + list);
        }
        // 검색어가 없는 경우, 모든 학과 목록을 반환
        else {
            list = managerService.selectAllMajor();
        }
        mav.addObject("list", list);
        mav.addObject("majorName", majorName);
        mav.addObject("collegeIdx", collegeIdx);
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
        UserDTO user = userService.getUserByUserId(lecture.getProfessor().getUser().getIdx());
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

    @GetMapping("/studentSituation")                // 학생 상태 조회
    public ModelAndView studentSituation(@RequestParam(required = false) String status) {
        ModelAndView mav = new ModelAndView("manager/studentSituation");

        // 검색어가 없는 경우에는 모든 학생 목록을 반환하고,
        // 검색어가 있는 경우에는 검색어를 포함하는 학생 목록을 반환
        List<SituationStuDto> studentList = situationServive.selectSituationStu(status);
        mav.addObject("studentList", studentList);
        return mav;
    }

    @GetMapping("/studentSituationView/{idx}")              // 학생 상태 변경을 위한 view
    public ModelAndView studentSituationView(@PathVariable("idx") Long idx) {
        ModelAndView mav = new ModelAndView("/manager/studentSituationView");
        SituationStuDto situation = situationServive.selectOneSituation(idx);
        log.info(situation.toString());
        mav.addObject("situation", situation);
        return mav;
    }



}
