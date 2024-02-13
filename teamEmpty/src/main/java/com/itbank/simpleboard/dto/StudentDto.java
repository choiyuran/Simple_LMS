package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class StudentDto {
    private Long idx;

    private String img = null;

    private Integer student_num;

    private Integer student_grade;

    private UserDTO user;

    private ProfessorDto professor;

    private MajorDto major;

    private Date enteranceDate;

    @QueryProjection
    public StudentDto(Long idx, Integer student_num, Integer student_grade, UserDTO user, ProfessorDto professor, MajorDto major, Date enteranceDate) {
        this.idx = idx;
        this.student_num = student_num;
        this.student_grade = student_grade;
        this.user = user;
        this.professor = professor;
        this.major = major;
        this.enteranceDate = enteranceDate;
    }
}
