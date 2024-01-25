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
import org.springframework.http.HttpStatus;
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

    @PostMapping("/addmanager")   // 교직원 등록
    public ResponseEntity<?> registerManager(@ModelAttribute UserFormDTO userFormDTO) {
        long startTime = System.currentTimeMillis();
        log.info("교직원등록");
//        yourService.processForm(formDTO);
        log.info(userFormDTO.getUserType());
        log.info(userFormDTO.getFirstName());

        // 응답 생성
        Map<String, String> response = new HashMap<>();
        response.put("message", "폼 등록이 완료되었습니다.");

        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.lectureListAjax 실행 시간: {} 밀리초", endTime - startTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addstudent")   // 학생 등록
    public String addStudent() {
        log.info("학생등록");
        return "common/register";
    }

    @ResponseBody
    @PostMapping("/addprofessor")   // 교수 등록
    public ResponseEntity<Map<String, String>> registerProfessor(@ModelAttribute UserFormDTO userFormDTO) {
        try {
            log.info("교수등록");
            long startTime = System.currentTimeMillis();
            // yourService.processForm(formDTO);
            log.info(userFormDTO.getUserType());
            log.info(userFormDTO.getPnum());
            log.info(userFormDTO.getBackSecurity());
            log.info(userFormDTO.getEmail());

            // 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("message", "폼 등록이 완료되었습니다.");
            response.put("name", userFormDTO.getLastName());
            response.put("email", "폼 등록이 완료되었습니다.");

            long endTime = System.currentTimeMillis();
            log.info("ProfessorController.lectureListAjax 실행 시간: {} 밀리초", endTime - startTime);

            return ResponseEntity.ok(response);
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

//    @GetMapping("/majorList")       // 학과 목록
//    public ModelAndView majorList() {
//        ModelAndView mav = new ModelAndView("manager/majorList");
//        List<Major> list = managerService.selectAllMajor();
//        mav.addObject("list", list);
//        return mav;
//    }

    @GetMapping("/majorList")       // 학과 목록
    public ModelAndView majorList(@RequestParam(value = "collegeName", required = false) String collegeName) {
        ModelAndView mav = new ModelAndView("manager/majorList");
        log.info(collegeName);
        if (collegeName != null && !collegeName.isEmpty()) {
            // 검색어가 존재하는 경우, 해당 단과 대학에 해당하는 학과 목록을 검색하여 결과를 반환
            List<Major> searchResults = managerService.searchByCollege(collegeName);
            mav.addObject("list", searchResults);
        } else {
            // 검색어가 존재하지 않는 경우, 모든 학과 목록을 반환
            List<Major> list = managerService.selectAllMajor();
            mav.addObject("list", list);
        }
        return mav;
    }

    @GetMapping("/getColleges")             // 학과 검색 ajax
    @ResponseBody
    public List<College> getColleges() {
        return collegeService.getAllColleges();
    }

//    @GetMapping("/searchByCollege")
//    public ModelAndView searchByCollege(@RequestParam("collegeName") String collegeName) {
//        ModelAndView mav = new ModelAndView("searchByCollege"); // 검색 결과를 보여줄 뷰 이름을 설정해주세요.
//
//        // 단과 대학 이름을 이용하여 검색 로직을 구현하고, 결과 데이터를 ModelAndView에 추가하세요.
//        List<Major> searchResults = majorService.searchByCollege(collegeName);
//        mav.addObject("majors", searchResults);
//
//        return mav;
//    }


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



}
