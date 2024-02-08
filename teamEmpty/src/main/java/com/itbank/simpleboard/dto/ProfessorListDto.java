package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.YesOrNo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Date;

@Data
public class ProfessorListDto {
    private Long idx;           // 교수 idx
    private String img;
    private Date hireDate;
    private String name;
    private String userid;
    private String address;
    private String pnum;
    private String email;
    private Long major_idx;
    private String major_name;

    @QueryProjection
    public ProfessorListDto(Long idx, String img, Date hireDate, String name, String userid, String address, String pnum, String email, Long major_idx, String major_name) {
        this.idx = idx;
        this.img = img;
        this.hireDate = hireDate;
        this.name = name;
        this.userid = userid;
        this.address = address;
        this.pnum = pnum;
        this.email = email;
        this.major_idx = major_idx;
        this.major_name = major_name;
    }
}
