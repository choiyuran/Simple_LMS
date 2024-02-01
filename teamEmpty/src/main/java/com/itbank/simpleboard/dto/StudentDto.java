package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long idx;

    private Integer student_num;

    private Integer student_grade;

    private UserDTO user;

    private ProfessorDto professor;

    private MajorDto major;

    private Date enteranceDate;
}
