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
    
    // 성적 입력에 사용할 필드(lecture_name 포함)(2월 1일)
    private Long student_idx;
    private String student_name;
    private Long lecture_idx;

    @QueryProjection
    public EnrollmentDto(Long idx, String professor_name, String lecture_name, Long professor_idx) {
        this.idx = idx;
        this.professor_name = professor_name;
        this.lecture_name = lecture_name;
        this.professor_idx = professor_idx;
    }

    public EnrollmentDto(Long student_idx, String student_name, Long lecture_idx, String lecture_name) {
        this.student_idx = student_idx;
        this.student_name = student_name;
        this.lecture_idx = lecture_idx;
        this.lecture_name = lecture_name;
    }
}
