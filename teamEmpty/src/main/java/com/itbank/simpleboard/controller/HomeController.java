package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.user.UserRepository;
import com.itbank.simpleboard.service.AcademicCalendarService;
import com.itbank.simpleboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AcademicCalendarService academicCalendarService;

    @GetMapping("/")
    public String root(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
//        에러 테스트용 예외발생 코드
//        int n = 10 / 0;
//        System.out.println(n);

        // home 에서 calendar 불러오기
        List<AcademicCalendar> calendar = academicCalendarService.findCalendarAll();

        model.addAttribute("calendar", calendar);

        return "home";
    }

    @GetMapping("/test1")
    public String test1() {
        return "manager/registerLecture";
    }

    @GetMapping("/test2")
    public String test2() {
        return "manager/registerLectureEvaluationStu";
    }

    @GetMapping("/test3")
    public String test3() {
        return "manager/registerMajor";
    }

    @GetMapping("/test4")
    public String test4() {
//        int n = 2/0;
        return "manager/studentStatManage";
    }

    @GetMapping("/list")
    public String list() {
        return "student/lectureList";
    }

    @GetMapping("/side")
    public String side() {
        return "layout/sidebar";
    }


    @GetMapping("/err")
    public String error() {
        int n = 10 / 0;
        System.out.println(n);
        return "home";
    }


    // 테스트용 학생 로그인
    @GetMapping("/logintest")
    public String loginTest(HttpSession session) {
        User user = userRepository.findById(4L).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setIdx(user.getIdx());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setPnum(user.getPnum());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setUser_address(user.getAddress());
        session.setAttribute("user", userDTO);
        return "home";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String user_id, @RequestParam String user_pw, HttpSession session, Model model) {
        String url = "index";
        UserDTO user = userService.getUser(user_id, user_pw);
        if (user == null) {
            model.addAttribute("msg", "정보가 일치하지 않습니다. 다시 확인해주세요.");
            return url;
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
                    session.setAttribute("user", student);
                    url = "redirect:/student/home";
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
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpSession session, RedirectAttributes ra, HttpServletRequest request) {
        log.info("비번 변경");
        String url = "";
        Object login = session.getAttribute("user");
        String nowPassword = request.getParameter("nowPassword");
        String newPassword = request.getParameter("newPassword");
        int result = userService.changePassword(login, nowPassword, newPassword);
        if (result != 0) {
            url = "redirect:/";
            ra.addFlashAttribute("msg","비밀번호가 변경되었습니다. 다시 로그인 해 주세요");
        } else {
            if (login instanceof StudentDto) {
                url = "redirect:/student/studentModify";
                ra.addFlashAttribute("msg", "비밀번호를 정확히 입력해 주세요");
            } else if (login instanceof ProfessorDto) {
                url = "redirect:/professor/professorModify";
                ra.addFlashAttribute("msg", "비밀번호를 정확히 입력해 주세요");
            } else if (login instanceof ManagerLoginDto) {
                url = "redirect:/manager/managerModify";
                ra.addFlashAttribute("msg", "비밀번호를 정확히 입력해 주세요");
            }
        }
        return url;
    }
}
