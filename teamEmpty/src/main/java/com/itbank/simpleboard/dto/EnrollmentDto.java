package com.itbank.simpleboard.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentDto {
    private Long idx;
    private String professor_name;
    private String lecture_name;
    private Long professor_idx;

    @QueryProjection
    public EnrollmentDto(Long idx, String professor_name, String lecture_name, Long professor_idx) {
        this.idx = idx;
        this.professor_name = professor_name;
        this.lecture_name = lecture_name;
        this.professor_idx = professor_idx;
    }
}
