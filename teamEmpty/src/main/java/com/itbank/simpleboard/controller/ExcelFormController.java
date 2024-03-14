package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.StudentFormDTO;
import com.itbank.simpleboard.service.ExcelFormService;
import com.itbank.simpleboard.service.ManagerService;
import com.itbank.simpleboard.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class ExcelFormController {
    private final ExcelFormService excelFormService;
    private final ManagerService managerService;
    private final PaymentsService paymentsService;


    @PostMapping("/addStudent")   // 학생 등록 엑셀 업로드
    public String uploadStudentList(@RequestParam("studentFile") MultipartFile studentFile, Model model) throws IOException {
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

        List<StudentFormDTO> studentFormDTOList= excelFormService.saveStudentDTOList(studentFile);
        model.addAttribute("students","학생등록");
        model.addAttribute("studentList", studentFormDTOList);

        // null이 없는 값이다.
        Boolean checked = true;

        for(StudentFormDTO dto : studentFormDTOList){
            if(hasNullField(dto)) {
                checked = false;
                break;
            }
        }

        model.addAttribute("checked", checked);
        model.addAttribute("collegeList",managerService.selectAllCollege());
        return "manager/registerStudentList";

    }

    public static Boolean hasNullField(Object obj) {
        for(Field field : obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(field.get(obj) == null){
                    return true;
                }
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        return false;
    }

    @GetMapping("/downloadStudentForm") // 엑셀폼 다운로드
    public ResponseEntity<byte[]> downloadStudentForm() throws IOException {

        // 엑셀 템플릿 파일을 클래스패스에서 로드
        ClassPathResource resource = new ClassPathResource("static/excelForm/studentUploadForm.xlsx");

        // 다운로드할 파일명 설정
        String filename = "학생등록폼(폼양식절대수정금지!!).xlsx";

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
}
