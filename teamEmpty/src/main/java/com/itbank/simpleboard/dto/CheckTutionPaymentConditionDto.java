package com.itbank.simpleboard.dto;

import lombok.Data;

@Data
public class CheckTutionPaymentConditionDto {
    // 학생이름과 학과번호
    private String username;
    private Long major_idx;
}
