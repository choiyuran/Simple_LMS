package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Date;

@Data
public class ProfessorUserDto {
    private Long user_idx;
    private Long professor_idx;
    private String name;
    private Date hireDate;

    @QueryProjection
    public ProfessorUserDto(Long professor_idx, Date hireDate, String name) {
        this.professor_idx = professor_idx;
        this.hireDate = hireDate;
        this.name = name;
    }

    public ProfessorUserDto(Long professor_idx, String name) {
        this.professor_idx = professor_idx;
        this.name = name;
    }
}
