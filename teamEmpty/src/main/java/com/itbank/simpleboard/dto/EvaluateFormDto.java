package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateFormDto {
    private Long enrollment_idx;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private String q4;
    private String q5;
}
