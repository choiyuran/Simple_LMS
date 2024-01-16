package com.itbank.simpleboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class ProfessorDto {
    private Long professor_idx;
    private String img;
    private UserDTO user;
    private MajorDto major;
    private Date hireDate;
}
