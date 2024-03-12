package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.dto.StudentFormDTO;
import com.itbank.simpleboard.dto.UserFormDTO;
import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.service.ManagerService;
import com.itbank.simpleboard.service.ProfessorService;
import com.itbank.simpleboard.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class RegisterController {
    private final ManagerService managerService;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @PostMapping("/addStudentList")   // 학생 등록
    public String saveStudentList(@RequestBody List<StudentFormDTO> studentList) {
        log.info("학생등록 리스트 저장: AJAX");
        // 전달된 학생 정보 확인
        String resultMsg;
        String msg = managerService.verificationStudentDTOList(studentList);
        if(msg.equals("성공")){
            resultMsg = managerService.addStudentList(studentList);
        }else{
            resultMsg = msg;
        }

        return resultMsg;
    }


    @GetMapping("/getMajorsByCollege")
    public List<MajorDto> getMajorList(@RequestParam String collegeName) {
        log.info("학과목록 불러오기: AJAX");
        return managerService.getMajorList(collegeName);
    }

    @GetMapping("/getProfessorByMajor")
    public List<ProfessorUserDto> getProfessorsByDepartment(@RequestParam String majorName) {
        log.info("교수목록 불러오기: AJAX");
        return professorService.getProfessorsByMajor(majorName);
    }



}
