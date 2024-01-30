package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
public class StudentDto {
    private Long idx;

    private Integer student_num;

    private Integer student_grade;

    private UserDTO user;

    private ProfessorDto professor;

    private MajorDto major;

    private Date enteranceDate;
}
