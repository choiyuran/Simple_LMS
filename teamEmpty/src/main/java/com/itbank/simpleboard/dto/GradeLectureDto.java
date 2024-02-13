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
    private String abolition; // 폐강 여부
    private String score; // 성적

    private Long professor_idx;
    private String professor_name;
    private String plan;
    private String major;
    private String location;
    private Integer lectureRoom;

    @QueryProjection
    public GradeLectureDto(Long idx, String name, Integer credit, String type, String semester, Integer grade, String abolition, String score, Long professor_idx, String professor_name, String plan, String major, String location, Integer lectureRoom) {
        this.idx = idx;
        this.name = name;
        this.credit = credit;
        this.type = type;
        this.semester = semester;
        this.grade = grade;
        this.abolition = abolition;
        this.score = score;
        this.professor_idx = professor_idx;
        this.professor_name = professor_name;
        this.plan = plan;
        this.major = major;
        this.location = location;
        this.lectureRoom = lectureRoom;
    }
}
