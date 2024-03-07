package com.itbank.simpleboard.dto;


import com.itbank.simpleboard.entity.YesOrNo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentDto {
    private Long idx;
    private String professor_name;
    private String lecture_name;
    private Long professor_idx;
    private YesOrNo visible;
    // 성적 입력에 사용할 필드(lecture_name 포함)(2월 1일)
    private Long student_idx;
    private Integer student_num;
    private String student_name;
    private Long lecture_idx;
    private Boolean hasGrade;

    // 2월 6일
    private String score;

    @QueryProjection
    public EnrollmentDto(Long idx, String professor_name, String lecture_name, Long professor_idx,YesOrNo visible) {
        this.idx = idx;
        this.professor_name = professor_name;
        this.lecture_name = lecture_name;
        this.professor_idx = professor_idx;
        this.visible = visible;
    }

    public EnrollmentDto(Long idx, Long student_idx, Integer student_num, String student_name, Long lecture_idx, String lecture_name, Boolean hasGrade, String score) {
        this.idx = idx;
        this.student_idx = student_idx;
        this.student_num = student_num;
        this.student_name = student_name;
        this.lecture_idx = lecture_idx;
        this.lecture_name = lecture_name;
        this.hasGrade = hasGrade;
        this.score = score;
    }
}
