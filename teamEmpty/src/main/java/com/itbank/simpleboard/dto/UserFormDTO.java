package com.itbank.simpleboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserFormDTO {
    // 교직원 아이디 111_입사년도_본인번호
    // 교수 아이디 222_입사년도_본인번호

    // 이름
    private String firstName;
    private String lastName;

    // 주민번호
    private String frontSecurity;
    private String backSecurity;

    // 주소
    private String address;

    // 연락처
    private String pnum;


    // 이메일
    private String email;

    // dtype
    private String userType;

    //직원사진
    private MultipartFile imageFile;

    // 입사일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    // 학과번호
    private Long major;


}
