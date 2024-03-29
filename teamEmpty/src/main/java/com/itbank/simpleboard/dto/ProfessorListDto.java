package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ProfessorListDto {
    private Long idx;           // 교수 idx
    private String img;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
    private String name;
    private String address;
    private String pnum;
    private String email;
    private Long major_idx;
    private String major_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    private MultipartFile professorImg;

    @QueryProjection
    public ProfessorListDto(Long idx, String img, Date hireDate, String name, String address, String pnum, String email, Long major_idx, String major_name, Date leaveDate) {
        this.idx = idx;
        this.img = img;
        this.hireDate = hireDate;
        this.name = name;
        this.address = address;
        this.pnum = pnum;
        this.email = email;
        this.major_idx = major_idx;
        this.major_name = major_name;
        this.leaveDate = leaveDate;
    }
}
