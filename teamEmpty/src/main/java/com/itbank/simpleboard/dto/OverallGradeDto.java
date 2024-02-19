package com.itbank.simpleboard.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OverallGradeDto {
    private String collegeName; // 단과대학
    private String majorName; // 학과
    private Integer grade; // 학년
    private Integer totalCredit;// 총 이수 학점
    private Double sum;
    private Double avg;

    @QueryProjection
    public OverallGradeDto(String collegeName, String majorName, Integer grade, Integer totalCredit, Double sum, Double avg) {
        this.collegeName = collegeName;
        this.majorName = majorName;
        this.grade = grade;
        this.totalCredit = totalCredit;
        this.sum = sum;
        this.avg = avg;
    }
}
