package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Status_type;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Date;

@Data
public class SituationStuDto {
    private Long idx;
    private Integer student_num;
    private String name;
    private String major;
    private Date start_date;
    private Date end_date;
    private Status_type status;
    private Long student_idx;
    private Long major_idx;
    private Long user_idx;

    @QueryProjection
    public SituationStuDto(Long idx, Integer student_num ,String name, String major, Date start_date, Date end_date, Status_type status, Long student_idx, Long major_idx, Long user_idx) {
        this.idx = idx;
        this.student_num = student_num;
        this.name = name;
        this.major = major;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.student_idx = student_idx;
        this.major_idx = major_idx;
        this.user_idx = user_idx;
    }
}
