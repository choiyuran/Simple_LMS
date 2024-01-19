package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ProfessorLectureDto {
    private Long idx;
    private String name;
    private String intro;
    private Integer credit;
    private String day;
    private String start;
    private String end;
    private String type;
    private Integer maxCount;
    private Integer currentCount;
    private String semester;
    private Integer grade;

    private String professor_name;
    private String plan;
    private String major;
    private String location;
    private Integer lectureRoom;

    @QueryProjection
    public ProfessorLectureDto(Long idx, String name, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, String professor_name, String plan, String major, String location, Integer lectureRoom) {
        this.idx = idx;
        this.name = name;
        this.intro = null;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.semester = semester;
        this.grade = grade;
        this.professor_name = professor_name;
        this.plan = plan;
        this.major = major;
        this.location = location;
        this.lectureRoom = lectureRoom;
    }

    @QueryProjection
    public ProfessorLectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, String professor_name, String plan, String major, String location, Integer lectureRoom) {
        this.idx = idx;
        this.name = name;
        this.intro = intro;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.semester = semester;
        this.grade = grade;
        this.professor_name = professor_name;
        this.plan = plan;
        this.major = major;
        this.location = location;
        this.lectureRoom = lectureRoom;
    }
}
