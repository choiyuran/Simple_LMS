package com.itbank.simpleboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class StudentFormDTO {
    private Long idx;

    // 학번
    private Integer student_num;

    // 이름
    private String name;

    // 주민번호
    private String security;

    // 연락처
    private String pnum;

    // 주소
    private String address;

    // 이메일
    private String email;

    // 입학일
    private Date entranceDate;

    // 학년
    private Integer student_grade;

    // 학과번호
    private Long major;

    // 지도교수번호
    private Long professor;



}
