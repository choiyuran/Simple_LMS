package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.YesOrNo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class CheckTuitionPaymentDto {

    // 학년
    private Integer student_grade;

    // 학번
    private Integer student_num;

    // 납부일자
    private Date date;

    // 납부 여부
    private YesOrNo flag;

    @QueryProjection

    public CheckTuitionPaymentDto(Integer student_grade, Integer student_num, Date date, YesOrNo flag) {
        this.student_grade = student_grade;
        this.student_num = student_num;
        this.date = date;
        this.flag = flag;
    }
}
