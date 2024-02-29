package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Status_type;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StudentListDto {
    public Long idx;            // 학생 idx
    public Integer student_num;
    public String name;
    public Long major_idx;
    public String major_name;
    public String pnum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date entranceDate;
    public String address;
    public String email;


    @QueryProjection
    public StudentListDto(Long idx, Integer student_num, String name, Long major_idx, String major_name, String pnum, Date entranceDate, String address, String email) {
        this.idx = idx;
        this.student_num = student_num;
        this.name = name;
        this.major_idx = major_idx;
        this.major_name = major_name;
        this.pnum = pnum;
        this.entranceDate = entranceDate;
        this.address = address;
        this.email = email;
    }
}
