package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsDto {
    private Long student;
    private String semester;
    private Integer tuition;
    private Integer scholarship;
}
