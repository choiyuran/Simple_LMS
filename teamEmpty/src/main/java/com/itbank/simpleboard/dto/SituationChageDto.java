package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Status_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituationChageDto {
    private String start_date;
    private String end_date;
    private Long student;
    private Status_type status;
}
