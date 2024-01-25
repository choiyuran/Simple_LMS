package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {
    private Long professor_idx;
    private String img;
    private UserDTO user;
    private MajorDto major;
    private Date hireDate;
}
