package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.Notice;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.user.UserRepository;
import com.itbank.simpleboard.service.AcademicCalendarService;
import com.itbank.simpleboard.service.NoticeService;
import com.itbank.simpleboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserService userService;
    private final AcademicCalendarService academicCalendarService;
    private final NoticeService noticeService;

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
    public String login(@RequestParam String user_id, @RequestParam String user_pw, HttpSession session, Model model) {
        String url = "/";
        if (!user_id.isEmpty() && !user_pw.isEmpty()) {
            UserDTO user = userService.getUser(user_id, user_pw);
            if (user == null) {
                model.addAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
            } else {
                switch (user.getRole().toString()) {
                    case "교수":
                        ProfessorDto professor = userService.getProfessor(user);
                        if (professor == null) {
                            model.addAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                        } else {
                            session.setAttribute("user", professor);
                            url = "redirect:/professor/home";
                        }
                        break;
                    case "학생":
                        StudentDto student = userService.getStudent(user);
                        if (student == null) {
                            model.addAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                        } else {
                            session.setAttribute("user", student);
                            url = "redirect:/student/home";
                        }
                        break;
                    case "교직원":
                        ManagerLoginDto manager = userService.getManager(user);
                        if (manager == null) {
                            model.addAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
                        } else {
                            session.setAttribute("user", manager);
                            url = "redirect:/manager/home";
                        }
                        break;
                }
            }
        } else {
            model.addAttribute("msg", "ID와 Password를 입력해주세요.");
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
    public Integer SendVerificationCode(String email){
        return userService.sendAuthNumber(email);
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
        } else {
            if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
            } else if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
            }
            ra.addFlashAttribute("msg", "회원 정보 수정에 실패하였습니다. 다시 시도해 주세요");
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
        } else {
            if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
            } else if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
            }
            ra.addFlashAttribute("msg", "비밀번호를 정확히 입력해주세요.");
        }
        return url;
    }

    @ResponseBody
    @PostMapping("findUserByUser_idAndEmail")
    public ResponseEntity<Map<String, Object>> findUser(@RequestBody Map<String, String> request) {
        log.info("findUser 시작");

        Map<String, Object> response = new HashMap<>();

        // Null 체크
        String userId = request.get("user_id");
        String email = request.get("email");

        if ((userId == null || userId.isEmpty()) || (email == null || email.isEmpty())) {
            response.put("result", 0);
            response.put("msg", "유효한 ID와 이메일을 입력하세요.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // 유효성 검사 메서드 호출
            Integer result = userService.checkByUser_idAndEmail(userId, email);
            response.put("result", result);

            if (result != 0) {
                response.put("msg", "인증번호가 발송되었습니다.");
            } else {
                response.put("msg", "ID 또는 이메일을 확인해주세요.");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리
            log.error("findUser 오류: " + e.getMessage(), e);
            response.put("result", 0);
            response.put("msg", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
