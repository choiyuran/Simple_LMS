package com.itbank.simpleboard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationDto {
    private Long enrollment_idx;
    private String professorName;
    private Integer studentNum;
    private String studentName;
    private String lectureName;
    private String majorName;
    private Long studentIdx;
    private Long professorIdx;
    private Long lectureIdx;
}
