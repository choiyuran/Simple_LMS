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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserService userService;
    private final ProfessorService professorService;
    private final AcademicCalendarService academicCalendarService;
    private final NoticeService noticeService;
    private final ManagerService managerService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String user_id, @RequestParam String user_pw, @RequestParam(required = false, defaultValue = "false") Boolean rememberMe, HttpSession session, RedirectAttributes ra, HttpServletResponse response) {
        String url = "redirect:/";
        if (!user_id.isEmpty() && !user_pw.isEmpty()) {
            UserDTO user = userService.getUser(user_id, user_pw);
            if (user == null) {
                ra.addFlashAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                ra.addFlashAttribute("title", "로그인 실패");
                ra.addFlashAttribute("icon", "error");
            } else {
                // '내 아이디 기억하기'가 체크되었을 때
                switch (user.getRole().toString()) {
                    case "교수":
                        ProfessorDto professor = userService.getProfessor(user);
                        if (professor == null) {
                            ra.addFlashAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                            ra.addFlashAttribute("title", "로그인 실패");
                            ra.addFlashAttribute("icon", "error");
                        } else {
                            session.setAttribute("user", professor);
                            ra.addFlashAttribute("msg", "로그인에 성공했습니다.");
                            ra.addFlashAttribute("title", "로그인 성공");
                            ra.addFlashAttribute("icon", "success");
                            url = "redirect:/professor/home";
                        }
                        break;
                    case "학생":
                        StudentDto student = userService.getStudent(user);
                        if (student == null) {
                            ra.addFlashAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                            ra.addFlashAttribute("title", "로그인 실패");
                            ra.addFlashAttribute("icon", "error");
                        } else {
                            session.setAttribute("user", student);
                            ra.addFlashAttribute("msg", "로그인에 성공했습니다.");
                            ra.addFlashAttribute("title", "로그인 성공");
                            ra.addFlashAttribute("icon", "success");
                            url = "redirect:/student/home";
                        }
                        break;
                    case "교직원":
                        ManagerLoginDto manager = userService.getManager(user);
                        if (manager == null) {
                            ra.addFlashAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                            ra.addFlashAttribute("title", "로그인 실패");
                            ra.addFlashAttribute("icon", "error");
                        } else {
                            session.setAttribute("user", manager);
                            ra.addFlashAttribute("msg", "로그인에 성공했습니다.");
                            ra.addFlashAttribute("title", "로그인 성공");
                            ra.addFlashAttribute("icon", "success");
                            url = "redirect:/manager/home";
                        }
                        break;
                }
                if (rememberMe) {
                    // 쿠키 생성
                    Cookie cookie = new Cookie("user_id", user_id);
                    // 쿠키 유효시간 설정(7일)
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    // 쿠키 저장
                    response.addCookie(cookie);
                }
            }
        } else {
            ra.addFlashAttribute("msg", "ID와 Password를 입력해주세요");
            ra.addFlashAttribute("title", "로그인 실패");
            ra.addFlashAttribute("icon", "error");
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("email-verification")                      // 이메일 인증
    public Integer SendVerificationCode(String email, HttpServletResponse response) {
        Integer authNumber = userService.sendAuthNumber(email);

        Cookie cookie = new Cookie("authNumber", authNumber.toString());
        cookie.setMaxAge(3 * 60); // 쿠키 유효시간을 3분으로 설정
        response.addCookie(cookie); // 클라이언트에 쿠키 추가

        return authNumber;
    }

    @GetMapping("/modifyCheck")
    public String modifyCheck(HttpSession session) {
        String url = "redirect:/login";
        Object login = session.getAttribute("user");
        if (login instanceof ProfessorDto) {
            url = "common/modifyCheck";
        } else if (login instanceof ManagerLoginDto) {
            url = "common/modifyCheck";
        } else if (login instanceof StudentDto) {
            url = "common/modifyCheck";
        }
        return url;
    }

    @PostMapping("/modifyCheck")
    public String modifyCheck(HttpSession session, @RequestParam("password") String password, Model model) {
        String url = "common/modifyCheck";
        Object login = session.getAttribute("user");
        int result = userService.checkPassword(login, password);
        if (result != 0) {
            if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
            } else if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
            }
        } else {
            model.addAttribute("msg", "비밀번호를 확인해주세요.");
        }
        return url;
    }

    @PostMapping("/userModify")
    public String userModify(HttpSession session, UserDTO param, RedirectAttributes ra) {  // 회원정보 수정
        String url = "";
        Object login = session.getAttribute("user");

        UserDTO userDTO = userService.userUpdate(login, param);
        if (userDTO != null) {
            if (login instanceof StudentDto) {
                StudentDto user = (StudentDto) login;
                user.setUser(userDTO);
                session.setAttribute("user", user);
                url = "redirect:/student/studentModify";
            } else if (login instanceof ProfessorDto) {
                ProfessorDto user = (ProfessorDto) login;
                user.setUser(userDTO);
                session.setAttribute("user", user);
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                ManagerLoginDto user = (ManagerLoginDto) login;
                user.setUser(userDTO);
                session.setAttribute("user", user);
                url = "redirect:/manager/managerModify";
            }
            ra.addFlashAttribute("msg", "회원 정보가 수정되었습니다.");
            ra.addFlashAttribute("title", "회원 정보 수정 완료");
            ra.addFlashAttribute("icon", "success");
        } else {
            if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
            } else if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
            }
            ra.addFlashAttribute("msg", "회원 정보 수정에 실패하였습니다. 다시 시도해 주세요");
            ra.addFlashAttribute("title", "회원 정보 수정 실패");
            ra.addFlashAttribute("icon", "error");
        }
        return url;
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpSession session, HttpServletRequest request, RedirectAttributes ra) {
        log.info("비번 변경");
        String url = "";
        Object login = session.getAttribute("user");
        int result = userService.changePassword(login, request.getParameter("nowPassword"), request.getParameter("newPassword"));
        if (result != 0) {
            url = "redirect:/";
            ra.addFlashAttribute("msg", "비밀번호가 수정되었습니다. 다시 로그인해주세요.");
            ra.addFlashAttribute("title", "비밀번호 수정 성공");
            ra.addFlashAttribute("icon", "success");
        } else {
            if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
            } else if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
            }
            ra.addFlashAttribute("msg", "비밀번호를 정확히 입력해주세요.");
            ra.addFlashAttribute("title", "비밀번호 수정 실패");
            ra.addFlashAttribute("icon", "error");
        }
        return url;
    }

    @ResponseBody
    @PostMapping("findUserByUser_idAndEmail")
    public ResponseEntity<Map<String, Object>> findUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        // Null 체크
        String userId = request.get("user_id");
        String email = request.get("email");

        if ((userId == null || userId.isEmpty()) || (email == null || email.isEmpty())) {
            response.put("result", 0);
            response.put("msg", "유효한 ID와 이메일을 입력하세요.");
            response.put("title", "계정 정보 찾기 실패");
            response.put("icon", "error");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // 유효성 검사 메서드 호출
            Integer result = userService.checkByUser_idAndEmail(userId, email);
            response.put("result", result);

            if (result != 0) {
                response.put("msg", "인증번호가 발송되었습니다.");
                response.put("title", "인증번호 발송 성공");
                response.put("icon", "success");
            } else {
                response.put("msg", "ID 또는 이메일을 확인해주세요.");
                response.put("title", "인증번호 발송 실패");
                response.put("icon", "error");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리
            log.error("findUser 오류: " + e.getMessage(), e);
            response.put("result", 0);
            response.put("msg", "서버 오류가 발생했습니다.");
            response.put("title", "계정 정보 찾기 실패");
            response.put("icon", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/findPassword")
    public String findPassword(HttpServletRequest request, RedirectAttributes ra) {
        log.info("findPassword 시작");
        String url = "redirect:/";
        int result = userService.changePassword(request.getParameter("user_id"), request.getParameter("newPassword"));
        if (result != 0) {
            ra.addFlashAttribute("msg", "비밀번호가 재설정 되었습니다.");
            ra.addFlashAttribute("title", "비밀번호 재설정 성공");
            ra.addFlashAttribute("icon", "success");
        } else {
            ra.addFlashAttribute("msg", "비밀번호 재설정에 실패하였습니다. 다시 시도해주세요.");
            ra.addFlashAttribute("title", "비밀번호 재설정 실패");
            ra.addFlashAttribute("icon", "error");
        }
        return url;
    }

    @GetMapping("/viewLecture/{idx}")   // 강의 상세보기
    public String viewLecture(@PathVariable("idx") Long idx, Model model) {
        model.addAttribute("lecture", professorService.getLectureDto(idx));
        return "common/viewLecture";
    }

    @GetMapping("/calendar") // 전체 학사일정 조회
    public String calendar(Model model) {
        List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();
        // Thymeleaf에서 편리하게 사용할 수 있도록 데이터 정리
        Map<Integer, List<AcademicCalendar>> calendarByMonth = calendar.stream()
                .collect(Collectors.groupingBy(cal -> cal.getStart_date().getMonthValue()));
        model.addAttribute("calendarByMonth", calendarByMonth);

        return "common/calendar";
    }

    @GetMapping("/noticeList")          // 공지 사항 조회
    public ModelAndView noticeList(@PageableDefault(size = 10) Pageable pageable) {
        ModelAndView mav = new ModelAndView("common/noticeList");
        Page<Notice> noticeList = noticeService.selectAll(pageable);
        mav.addObject("noticeList", noticeList);
        return mav;
    }

    @GetMapping("/noticeView/{idx}")      // 공지 사항 상세보기
    public ModelAndView noticeView(@PathVariable("idx") Long idx) {
        ModelAndView mav = new ModelAndView("common/noticeList");
        Notice notice = noticeService.selectOne(idx);
        if(notice != null) {
            noticeService.increaseViewCount(idx);
            mav.addObject("notice", notice);
            mav.setViewName("common/noticeView");
        }
        return mav;
    }

    @RequestMapping("/lectureList") // 강의 목록
    public String lectureList(Model model, @ModelAttribute LectureSearchConditionDto condition, @PageableDefault(size = 10) Pageable pageable) {
        long startTime = System.currentTimeMillis();

        Page<ProfessorLectureDto> lectureDtoList = professorService.getLectureDtoList(condition, pageable);
        String evaluationStatus = managerService.selectEvaluationStatus();
        // LectureDtoList를 Model에 추가
        model.addAttribute("LectureList", lectureDtoList);
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
        model.addAttribute("evaluationStatus", evaluationStatus);
        long endTime = System.currentTimeMillis();
        log.info("ProfessorController.lectureList(Get) 실행 시간: {} 밀리초", endTime - startTime);
        return "common/lectureList";
    }
}
