package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvaluateFormDto {
    private Long enrollment_idx;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private String q4;
    private String q5;

    @QueryProjection
    public EvaluateFormDto(Long enrollment_idx, Integer q1, Integer q2, Integer q3, String q4, String q5) {
        this.enrollment_idx = enrollment_idx;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }
}
