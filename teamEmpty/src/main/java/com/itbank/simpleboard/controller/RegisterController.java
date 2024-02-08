package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.dto.StudentFormDTO;
import com.itbank.simpleboard.dto.UserFormDTO;
import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.service.ManagerService;
import com.itbank.simpleboard.service.ProfessorService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class RegisterController {
    private final ManagerService managerService;
    private final ProfessorService professorService;

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

    //    @PostMapping(value = "/addProfessor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/addProfessor", produces = MediaType.APPLICATION_JSON_VALUE) // 교수 등록
    public ResponseEntity<Map<String, String>> registerProfessor(@ModelAttribute UserFormDTO userFormDTO) {
        try {
            log.info("교수등록 시작");
            long startTime = System.currentTimeMillis();

            log.info("사용자 타입: " + userFormDTO.getUserType());
            log.info("이메일: " + userFormDTO.getEmail());
            log.info("전공: " + userFormDTO.getMajor());

            Map<String, String> response = new HashMap<>();

            if ("professor".equals(userFormDTO.getUserType())) {
                Professor professor = managerService.addProfessor(userFormDTO);
                if (professor != null && professor.getProfessor_idx() != null) {
                    // 성공적으로 교수 등록이 완료된 경우
                    response.put("message", "폼 등록이 완료되었습니다.");
                    response.put("name", professor.getUser().getUser_name());
                    response.put("type", professor.getUser().getRole().toString());
                    log.info("교수 등록 성공: " + professor.getUser().getUser_name());
                    long endTime = System.currentTimeMillis();
                    log.info("교수 등록 처리 시간: {} 밀리초", endTime - startTime);
                    System.err.println("교수 등록 성공");
                    return ResponseEntity.ok(response);
                } else {
                    // 교수 등록에 실패한 경우
                    response.put("message", "교수 등록에 실패하였습니다");
                    log.error("교수 등록 실패");
                    System.err.println("교수 등록 실패");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 실패 시 500 에러 응답
                }
            } else {
                // 교수가 아닌 사용자 타입의 요청인 경우
                response.put("message", "교수 타입의 사용자가 아닙니다");
                log.error("교수 타입의 사용자가 아닙니다");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 잘못된 요청 400 에러 응답
            }
        } catch (Exception e) {
            log.error("교수 등록 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 예외 발생 시 500 에러 응답
        }
    }




    @PostMapping("/addStudentList")   // 학생 등록
    public String saveStudentList(@RequestBody List<StudentFormDTO> studentList, Model model) {
        log.info("학생등록 리스트 저장: AJAX");
        // 전달된 학생 정보를 반복하여 처리
        for (StudentFormDTO student : studentList) {
            // 각 학생의 정보를 업데이트하거나 저장하는 로직을 수행
            System.err.println("업데이트된 학생 정보: " + student);
        }

        model.addAttribute("message","학생 저장 완료");
        // 업데이트 결과에 대한 응답 메시지 반환
        return "학생 정보가 성공적으로 업데이트되었습니다.";
    }


    @GetMapping("/getMajorsByCollege")
    public List<MajorDto> getMajorList(@RequestParam String collegeName) {
        log.info("학과목록 불러오기: AJAX");
        List<MajorDto> majorList = managerService.getMajorList(collegeName);
        log.info("getMajorList / majorList" + majorList.get(0).toString());
        return majorList;
    }

    @GetMapping("/getProfessors")
    public List<ProfessorUserDto> getProfessorsByDepartment(@RequestParam Long majorIdx) {
        log.info("교수목록 불러오기: AJAX");
        return professorService.getProfessorsByMajor(majorIdx);
    }



}
