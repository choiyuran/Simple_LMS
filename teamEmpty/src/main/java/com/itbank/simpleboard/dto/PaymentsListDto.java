package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.YesOrNo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentsListDto {
    private Long idx;
    private String semester;
    private Integer tuition;
    private Integer scholarship;
    private String user_name;
    private String college_name;
    private String major_name;
    private Date date;
    private YesOrNo flag;

    @QueryProjection
    public PaymentsListDto(Long idx, String semester, Integer tuition, Integer scholarship, String user_name, String college_name, String major_name, Date date, YesOrNo flag) {
        this.idx = idx;
        this.semester = semester;
        this.tuition = tuition;
        this.scholarship = scholarship;
        this.user_name = user_name;
        this.college_name = college_name;
        this.major_name = major_name;
        this.date = date;
        this.flag = flag;
    }
}
