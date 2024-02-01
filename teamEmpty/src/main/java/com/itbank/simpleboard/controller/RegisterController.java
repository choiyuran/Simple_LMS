package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.UserFormDTO;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    private final ManagerService managerService;
    @PostMapping(value = "/manager/addProfessor", produces = MediaType.APPLICATION_JSON_VALUE) // 교수 등록
//    @ResponseBody// 교수 등록
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
                    return ResponseEntity.ok(response);
                } else {
                    // 교수 등록에 실패한 경우
                    response.put("message", "교수 등록에 실패하였습니다");
                    log.error("교수 등록 실패");
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
}
