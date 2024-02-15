package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.YesOrNo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckTuitionPaymentDto {

    private Long idx;
    private String user_name;
    private Integer grade;
    private Integer num;
    private Date date;
    private YesOrNo flag;
    private String semester;

    @QueryProjection
    public CheckTuitionPaymentDto(Long idx, String user_name, Integer grade, Integer num, Date date, YesOrNo flag, String semester) {
        this.idx = idx;
        this.user_name = user_name;
        this.grade = grade;
        this.num = num;
        this.date = date;
        this.flag = flag;
        this.semester = semester;
    }
}
