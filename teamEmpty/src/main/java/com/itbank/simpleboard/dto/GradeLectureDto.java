package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class GradeLectureDto {
    private Long idx;
    private String name; // 강의 이름
    private Integer credit; // 수강 학점
    private String type;  // 강의 타입
    private String semester; // 학기
    private Integer grade;  //  학년
    private String score; // 성적
    private String professor;// 교수
    private String major; // 전공

    @QueryProjection
    public GradeLectureDto(Long idx, String name, Integer credit, String type, String semester, Integer grade, String score, String professor, String major) {
        this.idx = idx;
        this.name = name;
        this.credit = credit;
        this.type = type;
        this.semester = semester;
        this.grade = grade;
        this.score = score;
        this.professor = professor;
        this.major = major;
    }
}
